import java.math.BigInteger;

public class Levi_Matthew_Base58Check {
    
    /**
     * Implements the Base58Check algorithm to convert a hex string to Base58 encoding.
     * 
     * @param hashResult A hex string like "00FFEEDD"
     * @return The Base58Check encoded string
     */
    public static String base58check(String hashResult) {
        String base58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
        BigInteger x = convertBytesToBigInteger(hashResult);
        StringBuilder out = new StringBuilder();
        
        while (x.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] res = x.divideAndRemainder(BigInteger.valueOf(58));
            x = res[0];
            int remainder = res[1].intValue();
            out.append(base58.charAt(remainder));
        }
        
        int leadingZeros = countLeadingZeroBytes(hashResult);
        
        while (leadingZeros > 0) {
            out.append(base58.charAt(0));
            leadingZeros -= 1;
        }
        
        out.reverse();
        
        return out.toString();
    }
    
    /**
     * Converts a hex string to a BigInteger.
     * 
     * @param hexString Hex string
     * @return The BigInteger representation
     */
    public static BigInteger convertBytesToBigInteger(String hexString) {
        hexString = hexString.replace("0x", "").toUpperCase();
        return new BigInteger(hexString, 16);
    }
    
    /**
     * Counts the number of leading zero bytes (00) in a hex string.
     * 
     * @param hexString Hex string
     * @return Number of leading zero bytes
     */
    public static int countLeadingZeroBytes(String hexString) {
        hexString = hexString.replace("0x", "").toUpperCase();
        
        if (hexString.length() % 2 != 0) {
            hexString = "0" + hexString;
        }
        
        int count = 0;
        int i = 0;
        while (i < hexString.length() - 1) {
            if (hexString.substring(i, i + 2).equals("00")) {
                count++;
                i += 2;
            } else {
                break;
            }
        }
        
        return count;
    }
    
    /**
     * Testing Method.
     */
    public static void main(String[] args) {
        System.out.println("Tests:");
        
        String[][] verificationCases = {
            {"fedcba98", "7Wqzj1"},
            {"00fedcba98", "17Wqzj1"},
            {"00f54a5851e9372b87810a8e60cdd2e7cfd80b6e31c7f18fe8", "1PMycacnJaSqwwJqjawXBErnLsZ7RkXUAs"},
            {"6fc9e94db7d8484b4de0adc62cd49e5de5e2c5605fd94e2ace", "myvZbq43us2pcKKevP1j7J1Fn4Fct9zk4D"},
            {"801e99423a4ed27608a15a2616a2b0e9e52ced330ac530edcc32c8ffc6a526aeddFF837EC4", "5J3mBbAH58CpQ3Y5RNJpUKPE62SQ5tfcvU2JpbnkeyhfsZgWFFh"}
        };
        
        for (String[] testCase : verificationCases) {
            String input = testCase[0];
            String expected = testCase[1];
            String actual = base58check(input);
            String pass = actual.equals(expected) ? "PASS" : "FAIL";
            System.out.printf("Input: %s%nExpected: %s%nActual:   %s [%s]%n%n", 
                            input, expected, actual, pass);
        }

        System.out.println(base58check("12345678"));
        System.out.println(base58check("0012345678"));
        System.out.println(base58check("005729f7d356615e74174c3e46fbb747b36523e2bad50b2737"));
        System.out.println(base58check("6fa6d9ee57dfb82898a61758fb53fa2223999fd3f557d9e675"));
        System.out.println(base58check("8024606dc6ece601de7e54be7cc706eb2e26a5b87fa3389858ce0b078d461b34057ef522c5"));

    }
}