using System;
using System.Security.Cryptography;
using System.Text;
using System.IO;
class Crypto
{

    byte[] key = Encoding.UTF8.GetBytes("FBX%*^*(fghnv@#$");
    byte[] iv = Encoding.UTF8.GetBytes("FBX%^^$$f05v@#$O");
    
    public Crypto(string key, string iv)
    {
        this.key = Encoding.UTF8.GetBytes(key);
        this.iv = Encoding.UTF8.GetBytes(iv);
    }
    public Crypto()
    {

    }
    public void Generate()
    {
        try
        {
            string text = "baoson";
            System.Console.WriteLine(".NET");
            string encrypted = encrypt(text);
            System.Console.WriteLine(text + " encrypted is " + encrypted);
            string decrypted = decrypt(encrypted);
            System.Console.WriteLine(encrypted + " decrypted is " + decrypted);
            Console.ReadLine();

        }
        catch (Exception e)
        {
            System.Console.WriteLine(e.StackTrace);
        }

    }

    public string encrypt(string text)
    {
        RijndaelManaged rijndaelCipher = new RijndaelManaged();

        rijndaelCipher.Mode = CipherMode.CBC;

        rijndaelCipher.Padding = PaddingMode.PKCS7;

        rijndaelCipher.KeySize = 128;

        rijndaelCipher.BlockSize = 128;

        rijndaelCipher.Key = key;

        rijndaelCipher.IV = iv;

        ICryptoTransform transform = rijndaelCipher.CreateEncryptor();

        byte[] plainText = Encoding.UTF8.GetBytes(text);

        byte[] cipherBytes = transform.TransformFinalBlock(plainText, 0, plainText.Length);

        return Convert.ToBase64String(cipherBytes);

    }

    public string decrypt(string text)
    {
        RijndaelManaged rijndaelCipher = new RijndaelManaged();

        rijndaelCipher.Mode = CipherMode.CBC;

        rijndaelCipher.Padding = PaddingMode.PKCS7;

        rijndaelCipher.KeySize = 128;

        rijndaelCipher.BlockSize = 128;

        byte[] encryptedData = Convert.FromBase64String(text);

        rijndaelCipher.Key = key;

        rijndaelCipher.IV = iv;

        ICryptoTransform transform = rijndaelCipher.CreateDecryptor();

        byte[] plainText = transform.TransformFinalBlock(encryptedData, 0, encryptedData.Length);

        return Encoding.UTF8.GetString(plainText);
    }
}

