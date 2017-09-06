import java.util.Arrays;

/**
 * 아나그램 알고리즘
 * 두개의 문자열 입력을 받아서 두 개의 관계가
 * 아나그램 관계인지 확인하는 프로그램을 개발하세요
 * @author daeho
 *
 */
public class Anagram {
	/** 
	 * listen == silent
	 * cat == act
	 * was it a cat i saw == was it a cat i saw
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean checkAnagram(String a, String b){
		char[] aChar = a.toLowerCase().replace(" ", "").toCharArray();
		char[] bChar = b.toLowerCase().replace(" ", "").toCharArray();
		
		if(aChar.length != bChar.length) return false;
		
		Arrays.sort(aChar);
		Arrays.sort(bChar);
		
		return Arrays.equals(aChar, bChar);
	}
}