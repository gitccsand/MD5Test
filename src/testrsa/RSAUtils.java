package testrsa;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtils {  
	  
    /** *//** 
     * �����㷨RSA 
     */  
    public static final String KEY_ALGORITHM = "RSA";  
      
    
    
    /**  
    * BASE64����  
    *   
    * @param key  
    * @return  
    * @throws Exception  
    */    
    public static byte[] decryptBASE64(String key) throws Exception {    
        return (new BASE64Decoder()).decodeBuffer(key);    
    }    
    /**  
    * BASE64����  
    *   
    * @param key  
    * @return  
    * @throws Exception  
    */    
    public static String encryptBASE64(byte[] key) throws Exception {    
        return (new BASE64Encoder()).encodeBuffer(key);    
    }    
  
   
    /**  
     * ����  
     * @param privateKey  
     * @param srcBytes  
     * @return  
     * @throws NoSuchAlgorithmException  
     * @throws NoSuchPaddingException  
     * @throws InvalidKeyException  
     * @throws IllegalBlockSizeException  
     * @throws BadPaddingException  
     */    
    protected byte[] encrypt(RSAPrivateKey privateKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{    
        if(privateKey!=null){    
            //Cipher������ɼ��ܻ���ܹ���������RSA    
            Cipher cipher = Cipher.getInstance("RSA");    
            //���ݹ�Կ����Cipher������г�ʼ��    
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);    
            byte[] resultBytes = cipher.doFinal(srcBytes);    
            return resultBytes;    
        }    
        return null;    
    }    
        
    /**  
     * ����   
     * @param publicKey  
     * @param srcBytes  
     * @return  
     * @throws NoSuchAlgorithmException  
     * @throws NoSuchPaddingException  
     * @throws InvalidKeyException  
     * @throws IllegalBlockSizeException  
     * @throws BadPaddingException  
     */    
    protected byte[] decrypt(RSAPublicKey publicKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{    
        if(publicKey!=null){    
            //Cipher������ɼ��ܻ���ܹ���������RSA    
            Cipher cipher = Cipher.getInstance("RSA");    
            //���ݹ�Կ����Cipher������г�ʼ��    
            cipher.init(Cipher.DECRYPT_MODE, publicKey);    
            byte[] resultBytes = cipher.doFinal(srcBytes);    
            return resultBytes;    
        }    
        return null;    
    }    
    
    /**  
     * @param args  
     * @throws NoSuchAlgorithmException   
     * @throws BadPaddingException   
     * @throws IllegalBlockSizeException   
     * @throws NoSuchPaddingException   
     * @throws InvalidKeyException   
     */    
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException ,Exception{    
    	RSAUtils rsa = new RSAUtils();    
        String msg = "<span style="+"font-family: Arial, Helvetica, sans-serif;"+">��Կ��˽Կ</span>";    
        //KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���    
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");    
        //��ʼ����Կ������������Կ��СΪ1024λ    
        keyPairGen.initialize(1024);    
        //����һ����Կ�ԣ�������keyPair��    
        KeyPair keyPair = keyPairGen.generateKeyPair();    
        //�õ�˽Կ    
        RSAPrivateKey rsaPirvateKey = (RSAPrivateKey)keyPair.getPrivate();                 
        //�õ���Կ    
        RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();    
            
          
          
        byte[] publicKeybyte =rsaPublicKey.getEncoded();  
        String publicKeyString = encryptBASE64(publicKeybyte);  
        System.out.println(publicKeyString);  
          
        byte[] privateKeybyte =rsaPirvateKey.getEncoded();  
        String privateKeyString = encryptBASE64(privateKeybyte);  
        System.out.println(privateKeyString);  
          
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);    
        PKCS8EncodedKeySpec privatekcs8KeySpec = new PKCS8EncodedKeySpec(decryptBASE64(privateKeyString));    
        PrivateKey privateKey= keyFactory.generatePrivate(privatekcs8KeySpec);            
   
          
        X509EncodedKeySpec publicpkcs8KeySpec = new X509EncodedKeySpec(decryptBASE64(publicKeyString));   
        PublicKey publicKey = keyFactory.generatePublic(publicpkcs8KeySpec);  
                  
          
        //��˽Կ����    
        byte[] srcBytes = msg.getBytes();    
        byte[] resultBytes = rsa.encrypt((RSAPrivateKey)privateKey, srcBytes);    
          
        String base64Msg= encryptBASE64(resultBytes);  
          
        byte[] base64MsgD = decryptBASE64(base64Msg);  
            
        //�ù�Կ����    
        byte[] decBytes = rsa.decrypt((RSAPublicKey) publicKey, base64MsgD);    
            
        System.out.println("������:" + msg);    
        System.out.println("˫�ؼ��ܺ���:" +base64Msg);    
        System.out.println("���ܺ���:" + new String(decBytes));    
    }    
 
}


///** *//** 
// * �����㷨RSA 
// */  
//public static final String KEY_ALGORITHM = "RSA";  
///** *//** 
// * ǩ���㷨 
// */  
//public static final String SIGNATURE_ALGORITHM = "MD5withRSA";  
//
///** *//** 
// * ��ȡ��Կ��key 
// */  
//private static final String PUBLIC_KEY = "RSAPublicKey";  
//  
///** *//** 
// * ��ȡ˽Կ��key 
// */  
//private static final String PRIVATE_KEY = "RSAPrivateKey";  
//  
///** *//** 
// * RSA���������Ĵ�С 
// */  
//private static final int MAX_ENCRYPT_BLOCK = 117;  
//  
///** *//** 
// * RSA���������Ĵ�С 
// */  
//private static final int MAX_DECRYPT_BLOCK = 128;  
/** *//** 
 * <p> 
 * ������Կ��(��Կ��˽Կ) 
 * </p> 
 *  
 * @return 
 * @throws Exception 
 */  
//public static Map<String, Object> genKeyPair() throws Exception {  
//    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);  
//    keyPairGen.initialize(1024);  
//    KeyPair keyPair = keyPairGen.generateKeyPair();  
//    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
//    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
//    Map<String, Object> keyMap = new HashMap<String, Object>(2);  
//    keyMap.put(PUBLIC_KEY, publicKey);  
//    keyMap.put(PRIVATE_KEY, privateKey);  
//    return keyMap;  
//}  
//    /** *//** 
//     * <p> 
//     * ��˽Կ����Ϣ��������ǩ�� 
//     * </p> 
//     *  
//     * @param data �Ѽ������� 
//     * @param privateKey ˽Կ(BASE64����) 
//     *  
//     * @return 
//     * @throws Exception 
//     */  
//    public static String sign(byte[] data, String privateKey) throws Exception {  
//    	
//        byte[] keyBytes = Base64Utils.decode(privateKey);  
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
//        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
//        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
//        signature.initSign(privateK);  
//        signature.update(data);  
//        return Base64Utils.encode(signature.sign());  
//    }  
//  
//    /** *//** 
//     * <p> 
//     * У������ǩ�� 
//     * </p> 
//     *  
//     * @param data �Ѽ������� 
//     * @param publicKey ��Կ(BASE64����) 
//     * @param sign ����ǩ�� 
//     *  
//     * @return 
//     * @throws Exception 
//     *  
//     */  
//    public static boolean verify(byte[] data, String publicKey, String sign)  
//            throws Exception {  
//        byte[] keyBytes = Base64Utils.decode(publicKey);  
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
//        PublicKey publicK = keyFactory.generatePublic(keySpec);  
//        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
//        signature.initVerify(publicK);  
//        signature.update(data);  
//        return signature.verify(Base64Utils.decode(sign));  
//    }  
//  
//    /** *//** 
//     * <P> 
//     * ˽Կ���� 
//     * </p> 
//     *  
//     * @param encryptedData �Ѽ������� 
//     * @param privateKey ˽Կ(BASE64����) 
//     * @return 
//     * @throws Exception 
//     */  
//    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)  
//            throws Exception {  
//        byte[] keyBytes = Base64Utils.decode(privateKey);  
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
//        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
//        cipher.init(Cipher.DECRYPT_MODE, privateK);  
//        int inputLen = encryptedData.length;  
//        ByteArrayOutputStream out = new ByteArrayOutputStream();  
//        int offSet = 0;  
//        byte[] cache;  
//        int i = 0;  
//        // �����ݷֶν���  
//        while (inputLen - offSet > 0) {  
//            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
//                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
//            } else {  
//                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
//            }  
//            out.write(cache, 0, cache.length);  
//            i++;  
//            offSet = i * MAX_DECRYPT_BLOCK;  
//        }  
//        byte[] decryptedData = out.toByteArray();  
//        out.close();  
//        return decryptedData;  
//    }  
//  
//    /** *//** 
//     * <p> 
//     * ��Կ���� 
//     * </p> 
//     *  
//     * @param encryptedData �Ѽ������� 
//     * @param publicKey ��Կ(BASE64����) 
//     * @return 
//     * @throws Exception 
//     */  
//    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)  
//            throws Exception {  
//        byte[] keyBytes = Base64Utils.decode(publicKey);  
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
//        Key publicK = keyFactory.generatePublic(x509KeySpec);  
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
//        cipher.init(Cipher.DECRYPT_MODE, publicK);  
//        int inputLen = encryptedData.length;  
//        ByteArrayOutputStream out = new ByteArrayOutputStream();  
//        int offSet = 0;  
//        byte[] cache;  
//        int i = 0;  
//        // �����ݷֶν���  
//        while (inputLen - offSet > 0) {  
//            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
//                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
//            } else {  
//                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
//            }  
//            out.write(cache, 0, cache.length);  
//            i++;  
//            offSet = i * MAX_DECRYPT_BLOCK;  
//        }  
//        byte[] decryptedData = out.toByteArray();  
//        out.close();  
//        return decryptedData;  
//    }  
//  
//    /** *//** 
//     * <p> 
//     * ��Կ���� 
//     * </p> 
//     *  
//     * @param data Դ���� 
//     * @param publicKey ��Կ(BASE64����) 
//     * @return 
//     * @throws Exception 
//     */  
//    public static byte[] encryptByPublicKey(byte[] data, String publicKey)  
//            throws Exception {  
//        byte[] keyBytes = Base64Utils.decode(publicKey);  
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
//        Key publicK = keyFactory.generatePublic(x509KeySpec);  
//        // �����ݼ���  
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
//        cipher.init(Cipher.ENCRYPT_MODE, publicK);  
//        int inputLen = data.length;  
//        ByteArrayOutputStream out = new ByteArrayOutputStream();  
//        int offSet = 0;  
//        byte[] cache;  
//        int i = 0;  
//        // �����ݷֶμ���  
//        while (inputLen - offSet > 0) {  
//            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
//                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
//            } else {  
//                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
//            }  
//            out.write(cache, 0, cache.length);  
//            i++;  
//            offSet = i * MAX_ENCRYPT_BLOCK;  
//        }  
//        byte[] encryptedData = out.toByteArray();  
//        out.close();  
//        return encryptedData;  
//    }  
//  
//    /** *//** 
//     * <p> 
//     * ˽Կ���� 
//     * </p> 
//     *  
//     * @param data Դ���� 
//     * @param privateKey ˽Կ(BASE64����) 
//     * @return 
//     * @throws Exception 
//     */  
//    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)  
//            throws Exception {  
//        byte[] keyBytes = Base64Utils.decode(privateKey);  
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
//        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
//        cipher.init(Cipher.ENCRYPT_MODE, privateK);  
//        int inputLen = data.length;  
//        ByteArrayOutputStream out = new ByteArrayOutputStream();  
//        int offSet = 0;  
//        byte[] cache;  
//        int i = 0;  
//        // �����ݷֶμ���  
//        while (inputLen - offSet > 0) {  
//            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
//                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
//            } else {  
//                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
//            }  
//            out.write(cache, 0, cache.length);  
//            i++;  
//            offSet = i * MAX_ENCRYPT_BLOCK;  
//        }  
//        byte[] encryptedData = out.toByteArray();  
//        out.close();  
//        return encryptedData;  
//    }  
//  
//    /** *//** 
//     * <p> 
//     * ��ȡ˽Կ 
//     * </p> 
//     *  
//     * @param keyMap ��Կ�� 
//     * @return 
//     * @throws Exception 
//     */  
//    public static String getPrivateKey(Map<String, Object> keyMap)  
//            throws Exception {  
//        Key key = (Key) keyMap.get(PRIVATE_KEY);  
//        return Base64Utils.encode(key.getEncoded());  
//    }  
//  
//    /** *//** 
//     * <p> 
//     * ��ȡ��Կ 
//     * </p> 
//     *  
//     * @param keyMap ��Կ�� 
//     * @return 
//     * @throws Exception 
//     */  
//    public static String getPublicKey(Map<String, Object> keyMap)  
//            throws Exception {  
//        Key key = (Key) keyMap.get(PUBLIC_KEY);  
//        return Base64Utils.encode(key.getEncoded());  
//    }  
  