/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Base64
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.pairip.licensecheck;

import android.os.Bundle;
import android.util.Base64;
import com.pairip.licensecheck.LicenseCheckException;
import com.pairip.licensecheck.LicenseClient;
import com.pairip.licensecheck.RepeatedCheckMetadata;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

public class LicenseResponseHelper {
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String PAYLOAD_LICENSE_DATA = "LICENSE_DATA";
    private static final String PAYLOAD_PACKAGE_NAME = "packageName";
    private static final String PAYLOAD_REPEATED_CHECK_DURATION_TO_RETRY_MILLIS = "repeatedCheckDurationToRetryMillis";
    private static final String PAYLOAD_REPEATED_CHECK_TIME_TO_RETRY_MILLIS = "repeatedCheckTimeToRetryMillis";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private LicenseResponseHelper() {
    }

    private static JSONObject base64ToJson(String object) throws LicenseCheckException {
        try {
            object = Base64.decode((String)object, (int)8);
            String string = new String((byte[])object, UTF_8);
            object = new JSONObject(string);
            return object;
        }
        catch (IllegalArgumentException | JSONException throwable) {
            throw new LicenseCheckException("Invalid response", throwable);
        }
    }

    private static String[] getJwsPartsForLicenseData(Bundle object) throws LicenseCheckException {
        if ((object = object.getString(PAYLOAD_LICENSE_DATA)) != null) {
            if (((Object)(object = ((String)object).split("\\.", -1))).length == 3) {
                return object;
            }
            throw new LicenseCheckException("Invalid response");
        }
        throw new LicenseCheckException("Invalid response");
    }

    private static PublicKey getPublicKey() throws LicenseCheckException {
        try {
            Object object = Base64.decode((String)LicenseClient.getLicensePubKey(), (int)0);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec((byte[])object);
            object = keyFactory.generatePublic(x509EncodedKeySpec);
            return object;
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            throw new LicenseCheckException("Could not create key specification from the public key", invalidKeySpecException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new LicenseCheckException("Could not decode public key", illegalArgumentException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new LicenseCheckException(String.format("%s algorithm not found on device", KEY_FACTORY_ALGORITHM), noSuchAlgorithmException);
        }
    }

    public static RepeatedCheckMetadata getRepeatedCheckMetadata(Bundle object) throws LicenseCheckException {
        try {
            object = LicenseResponseHelper.base64ToJson(LicenseResponseHelper.getJwsPartsForLicenseData(object)[1]);
            if (object.has(PAYLOAD_REPEATED_CHECK_DURATION_TO_RETRY_MILLIS) && object.has(PAYLOAD_REPEATED_CHECK_TIME_TO_RETRY_MILLIS)) {
                object = new RepeatedCheckMetadata(object.getLong(PAYLOAD_REPEATED_CHECK_DURATION_TO_RETRY_MILLIS), object.getLong(PAYLOAD_REPEATED_CHECK_TIME_TO_RETRY_MILLIS));
                return object;
            }
            return null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new LicenseCheckException("Invalid repeated check payload", illegalArgumentException);
        }
        catch (JSONException jSONException) {
            throw new LicenseCheckException("Could not decode json", jSONException);
        }
    }

    public static void validateResponse(Bundle object, String string) throws LicenseCheckException {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        Object object2;
        try {
            object2 = LicenseResponseHelper.getJwsPartsForLicenseData(object);
            jSONObject2 = LicenseResponseHelper.base64ToJson(object2[0]);
            jSONObject = LicenseResponseHelper.base64ToJson(object2[1]);
        }
        catch (JSONException jSONException) {
            throw new LicenseCheckException("Could not decode json", jSONException);
        }
        object = object2[2];
        String string2 = object2[0];
        object2 = object2[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".");
        stringBuilder.append((String)object2);
        string2 = stringBuilder.toString();
        if (jSONObject2.getString("alg").equals("RS256")) {
            LicenseResponseHelper.verifySignature(string2, (String)object, SIGNATURE_ALGORITHM, LicenseResponseHelper.getPublicKey());
            if (jSONObject.getString(PAYLOAD_PACKAGE_NAME).equals(string)) {
                return;
            }
            object = new LicenseCheckException("Package name doesn't match.");
            throw object;
        }
        object = new LicenseCheckException("Response must be signed with RS256 algorithm.");
        throw object;
    }

    private static void verifySignature(String object, String string, String string2, PublicKey publicKey) throws LicenseCheckException {
        try {
            Signature signature = Signature.getInstance(string2);
            signature.initVerify(publicKey);
            signature.update(((String)object).getBytes(UTF_8));
            if (signature.verify(Base64.decode((String)string, (int)8))) {
                return;
            }
            object = new LicenseCheckException("Signature verification failed.");
            throw object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new LicenseCheckException("Could not base64 decode returned signature", illegalArgumentException);
        }
        catch (SignatureException signatureException) {
            throw new LicenseCheckException("Could not parse returned signature.", signatureException);
        }
        catch (InvalidKeyException invalidKeyException) {
            throw new LicenseCheckException("Could not sign data with the public key", invalidKeyException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new LicenseCheckException(String.format("Could not find %s algorithm on the device", string2), noSuchAlgorithmException);
        }
    }
}

