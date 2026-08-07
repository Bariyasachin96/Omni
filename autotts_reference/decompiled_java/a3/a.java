/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 */
package a3;

import android.text.TextUtils;
import android.util.Log;
import b3.b;
import com.google.android.vending.licensing.DeviceLimiter;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ResponseData;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class a {
    public final Policy a;
    public final LicenseCheckerCallback b;
    public final int c;
    public final String d;
    public final String e;
    public final DeviceLimiter f;

    public a(Policy policy, DeviceLimiter deviceLimiter, LicenseCheckerCallback licenseCheckerCallback, int n3, String string, String string2) {
        this.a = policy;
        this.f = deviceLimiter;
        this.b = licenseCheckerCallback;
        this.c = n3;
        this.d = string;
        this.e = string2;
    }

    public LicenseCheckerCallback a() {
        return this.b;
    }

    public int b() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    public final void d(int n3) {
        this.b.b(n3);
    }

    public final void e() {
        this.b.c(561);
    }

    public final void f(int n3, ResponseData responseData) {
        this.a.b(n3, responseData);
        if (this.a.a()) {
            this.b.a(n3);
            return;
        }
        this.b.c(n3);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void g(PublicKey object, int n3, String object2, String string) {
        if (n3 != 0 && n3 != 1 && n3 != 2) {
            object = null;
            object2 = null;
        } else {
            try {
                if (TextUtils.isEmpty((CharSequence)object2)) {
                    Log.e((String)"LicenseValidator", (String)"Signature verification failed: signedData is empty. (Device not signed-in to any Google accounts?)");
                    this.e();
                    return;
                }
            }
            catch (SignatureException signatureException2) {
                throw new RuntimeException(signatureException2);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException2) {
                throw new RuntimeException(noSuchAlgorithmException2);
            }
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify((PublicKey)object);
            signature.update(((String)object2).getBytes());
            if (!signature.verify(b3.a.a(string))) {
                Log.e((String)"LicenseValidator", (String)"Signature verification failed.");
                this.e();
                return;
            }
            object2 = ResponseData.a((String)object2);
            if (((ResponseData)object2).a != n3) {
                Log.e((String)"LicenseValidator", (String)"Response codes don't match.");
                this.e();
                return;
            }
            if (((ResponseData)object2).b != this.c) {
                Log.e((String)"LicenseValidator", (String)"Nonce doesn't match.");
                this.e();
                return;
            }
            if (!((ResponseData)object2).c.equals(this.d)) {
                Log.e((String)"LicenseValidator", (String)"Package name doesn't match.");
                this.e();
                return;
            }
            if (!((ResponseData)object2).d.equals(this.e)) {
                Log.e((String)"LicenseValidator", (String)"Version codes don't match.");
                this.e();
                return;
            }
            object = ((ResponseData)object2).e;
            if (TextUtils.isEmpty((CharSequence)object)) {
                Log.e((String)"LicenseValidator", (String)"User identifier is empty.");
                this.e();
                return;
            }
        }
        if (n3 != 0) {
            if (n3 == 1) {
                this.f(561, (ResponseData)object2);
                return;
            }
            if (n3 != 2) {
                if (n3 == 3) {
                    this.d(3);
                    return;
                }
                if (n3 == 4) {
                    Log.w((String)"LicenseValidator", (String)"An error has occurred on the licensing server.");
                    this.f(291, (ResponseData)object2);
                    return;
                }
                if (n3 == 5) {
                    Log.w((String)"LicenseValidator", (String)"Licensing server is refusing to talk to this device, over quota.");
                    this.f(291, (ResponseData)object2);
                    return;
                }
                switch (n3) {
                    default: {
                        Log.e((String)"LicenseValidator", (String)"Unknown response code for license check.");
                        this.e();
                        return;
                    }
                    case 259: {
                        this.d(2);
                        return;
                    }
                    case 258: {
                        this.d(1);
                        return;
                    }
                    case 257: 
                }
                Log.w((String)"LicenseValidator", (String)"Error contacting licensing server.");
                this.f(291, (ResponseData)object2);
                return;
            }
        }
        this.f(this.f.a((String)object), (ResponseData)object2);
        return;
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e((String)"LicenseValidator", (String)"Could not parse response.");
            this.e();
            return;
        }
        catch (b b3) {
            Log.e((String)"LicenseValidator", (String)"Could not Base64-decode signature.");
            this.e();
            return;
        }
        catch (InvalidKeyException invalidKeyException) {
            this.d(5);
            return;
        }
    }
}

