/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.vending.licensing;

import b3.a;
import b3.b;
import com.google.android.vending.licensing.Obfuscator;
import com.google.android.vending.licensing.ValidationException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESObfuscator
implements Obfuscator {
    public static final byte[] c = new byte[]{16, 74, 71, -80, 32, 101, -47, 72, 117, -14, 0, -29, 70, 65, -12, 74};
    public Cipher a;
    public Cipher b;

    public AESObfuscator(byte[] object, String object2, String object3) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWITHSHAAND256BITAES-CBC-BC");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object2);
            stringBuilder.append((String)object3);
            Object object4 = new PBEKeySpec(stringBuilder.toString().toCharArray(), (byte[])object, 1024, 256);
            object2 = secretKeyFactory.generateSecret((KeySpec)object4);
            object = new SecretKeySpec;
            super(object2.getEncoded(), "AES");
            this.a = object4 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            object2 = c;
            object3 = new IvParameterSpec((byte[])object2);
            ((Cipher)object4).init(1, (Key)object, (AlgorithmParameterSpec)object3);
            this.b = object3 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            object4 = new IvParameterSpec((byte[])object2);
            ((Cipher)object3).init(2, (Key)object, (AlgorithmParameterSpec)object4);
            return;
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new RuntimeException("Invalid environment", generalSecurityException);
        }
    }

    @Override
    public String a(String string, String string2) {
        UnsupportedEncodingException unsupportedEncodingException2;
        block4: {
            if (string == null) {
                return null;
            }
            try {
                Cipher cipher = this.a;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("com.google.android.vending.licensing.AESObfuscator-1|");
                stringBuilder.append(string2);
                stringBuilder.append(string);
                string = b3.a.e(cipher.doFinal(stringBuilder.toString().getBytes("UTF-8")));
                return string;
            }
            catch (GeneralSecurityException generalSecurityException) {
            }
            catch (UnsupportedEncodingException unsupportedEncodingException2) {
                break block4;
            }
            throw new RuntimeException("Invalid environment", generalSecurityException);
        }
        throw new RuntimeException("Invalid environment", unsupportedEncodingException2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String b(String string, String charSequence) {
        b b32;
        block9: {
            IllegalBlockSizeException illegalBlockSizeException2;
            block8: {
                BadPaddingException badPaddingException2;
                block7: {
                    Serializable serializable;
                    if (string == null) {
                        return null;
                    }
                    try {
                        String string2 = new String(this.b.doFinal(b3.a.a(string)), "UTF-8");
                        serializable = new StringBuilder();
                        serializable.append("com.google.android.vending.licensing.AESObfuscator-1|");
                        serializable.append((String)charSequence);
                        if (string2.indexOf(serializable.toString()) == 0) {
                            return string2.substring(53 + ((String)charSequence).length(), string2.length());
                        }
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        throw new RuntimeException("Invalid environment", unsupportedEncodingException);
                    }
                    catch (BadPaddingException badPaddingException2) {
                        break block7;
                    }
                    catch (IllegalBlockSizeException illegalBlockSizeException2) {
                        break block8;
                    }
                    catch (b b32) {
                        break block9;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Header not found (invalid data or key):");
                    ((StringBuilder)charSequence).append(string);
                    serializable = new ValidationException(((StringBuilder)charSequence).toString());
                    throw serializable;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(badPaddingException2.getMessage());
                stringBuilder.append(":");
                stringBuilder.append(string);
                throw new ValidationException(stringBuilder.toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(illegalBlockSizeException2.getMessage());
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(string);
            throw new ValidationException(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(b32.getMessage());
        ((StringBuilder)charSequence).append(":");
        ((StringBuilder)charSequence).append(string);
        throw new ValidationException(((StringBuilder)charSequence).toString());
    }
}

