package dev.felicity.felicitycbt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

class EncUtil {

    private static SecretKey secret=null;

    private static void generateKey(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        //generate key
        byte salt[] = new byte[20];
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        secret = new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    static String encryptMsg(String message, String uid)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeySpecException {
        if(secret==null){
            generateKey(uid);
        }
        /* Encrypt the message. */
        Cipher cipher;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return new String(cipherText, "ISO-8859-1");
    }

    static String decryptMsg(String cipherText,String uid)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException {
        /* Decrypt the message*/
        if(secret==null){
            generateKey(uid);
        }
        Cipher cipher;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText.getBytes("ISO-8859-1")), "UTF-8");
    }
    static HashMap<String, Object> encMap(HashMap<String, Object> map, String uid) throws Exception{

        HashMap<String,Object> temp= new HashMap<String,Object>();

        for (String key: map.keySet()){
            if(map.get(key) instanceof HashMap){
                temp.put(key,encMap((HashMap<String,Object>) map.get(key),uid));
            }
            else if(map.get(key) instanceof ArrayList){
                ArrayList<String> tempList= new ArrayList<String>();
                for(Object word: (ArrayList<Object>) map.get(key)){
                    if(word instanceof String){
                        tempList.add(encryptMsg((String)word,uid));
                    }
                    else if(word instanceof Integer){
                        tempList.add(encryptMsg(((Integer)word)+"",uid));
                    }
                }
                temp.put(key,tempList);
            }
            else if(map.get(key) instanceof String){
                String enc=encryptMsg((String)map.get(key),uid);
                temp.put(key,enc);
            }
            else if(map.get(key) instanceof Integer){
                String enc=encryptMsg((Integer)map.get(key)+"",uid);
                temp.put(key,enc);
            }
        }
        return temp;

    }

    static HashMap<String, Object> decMap(HashMap<String, Object> map, String uid) throws Exception{

        HashMap<String,Object> temp= new HashMap<String,Object>();

        for (String key: map.keySet()){
            if(map.get(key) instanceof HashMap){
                temp.put(key,decMap((HashMap<String,Object>) map.get(key),uid));
            }
            else if(map.get(key) instanceof ArrayList){
                ArrayList<String> tempList= new ArrayList<String>();
                for(Object word: (ArrayList<Object>) map.get(key)){
                    if(word instanceof String){
                        tempList.add(decryptMsg((String)word,uid));
                    }
                    else if(word instanceof Integer){
                        tempList.add(decryptMsg(((Integer)word)+"",uid));
                    }
                }
                temp.put(key,tempList);
            }
            else if(map.get(key) instanceof String){
                String enc=decryptMsg((String)map.get(key),uid);
                temp.put(key,enc);
            }
            else if(map.get(key) instanceof Integer){
                String enc=decryptMsg((Integer)map.get(key)+"",uid);
                temp.put(key,enc);
            }
        }
        return temp;

    }
}
