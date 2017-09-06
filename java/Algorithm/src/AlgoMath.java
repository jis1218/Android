import java.util.Random;

public class AlgoMath {
	/**
	 * 로또번호 생성기
	 * 랜덤으로 번호 받아올때 중복된 값이있을 수 있으므로 중복체크 필요함
	 * @return
	 */
	public int[] getLottoNumber(){
		int[] result = new int[6];
		Random random = new Random();
		
		for(int i = 0; i < 6; i++) {
			int num = 0;
			do{
				num = random.nextInt(45) + 1;
			}while(existNum(result, num));
			
			result[i] = num;
		}
		
		return result;
	}
	
	/**
	 * nums에 num이 포함된 여부확인
	 * @param nums
	 * @param num
	 * @return
	 */
	private boolean existNum(int[] nums, int num){
		for(int item : nums) if(item == num) return true;
		
		return false;
	}
	
	public void testMath(){
		// 절대값 구하기
		int a = Math.abs(-123);
		
		// 반올림
		long b = Math.round(123.5);
		
		// 올림
		double c = Math.ceil(123.4);
		
		// 내림
		double d = Math.floor(123.5);
		
		Math.random(); // 0보다 크가나 같고 1보다 작은 실수를 리턴
		
		Random random = new Random();
		// 1부터 100사이의 랜덤한 숫자 가져오기
		random.nextInt(100); // 0 ~ 99사이의 정수가 리턴
		int r = random.nextInt(100) + 1; // 1~ 100사이의 정수가 리턴
	}
}
