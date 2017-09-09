import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * 데이터를 저장하는 저장소를 관리하는 클래스
 * 
 * @author daeho
 *
 */
public class ModelWithDB {
	private final String URL = "jdbc:mysql://localhost:3306/memo";
	private final String USER = "root";
	private final String PASS = "root";
	
	/**
	 * new 하는 순간 실행됨 (생성자)
	 */
	public ModelWithDB(){
		// 특정 컴퓨터를 찾기위한 주소 체계
		// 아이피 = 213.12.142.132
		// url = naver.com
		//
		// 특정 프로그램에 할당되는 세부 번지
		// port = 1 ~ 6만번대
		//        2000번대 밑은 이미 표준으로 사용되고 있다.
		//
		// 소켓 : 아이피 + 포트
		
		// 표준 프로토콜
		// http://아이피(주소) : 포트(80)
		
		// 특정 프로그램에 액세스 하기위한 주소체계
		// 프로토콜이름 :// 아이피(주소) : 포트
		
		// ("데이터베이스주소","아이디","비밀번호")
		try{
			Class.forName("com.mysql.jdbc.Driver"); //드라이버를 동적으로 로드
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private Connection getConnection(){
		try{
			return DriverManager.getConnection(URL, USER, PASS);
		}catch(Exception ex){
			System.out.println("접속실패");
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Memo> getList(){
		
		ArrayList<Memo> list = new ArrayList<Memo>();
		
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return list;
		
		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " select * from memo ";
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			Statement stmt = conn.createStatement();
			// 2.3 select한 결과값을 돌려받기 위해 쿼리를 실행
			ResultSet rs = stmt.executeQuery(sql);
			// 결과셋을 반복하면서 하나씩 꺼낼 수 있다
			while(rs.next()){
				Memo memo = new Memo(rs.getString("name"), rs.getString("content"));
				memo.setNo(rs.getInt("no"));
				memo.setDatetime(rs.getLong("datetime"));
				
				list.add(memo);
			}
			
			// 3. 데이터베이스 연결해제
			conn.close();	
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * list에 memo 추가
	 * @param memo
	 */
	public void create(Memo memo){
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return;
		
		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " insert into memo(name,content,datetime) values(?,?,?) ";
			
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 2.3 물음표에 값을 세팅
			pstmt.setString(1, memo.getName());
			pstmt.setString(2, memo.getContent());
			pstmt.setTimestamp(3, new Timestamp( System.currentTimeMillis()));
			// 2.4 쿼리를 실행
			pstmt.executeUpdate();
			
			// 3. 데이터베이스 연결해제
			conn.close();	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * list의 memo 삭제
	 * @param memo
	 */
	public void delete(Memo memo){
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return;
		
		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " delete from memo where no = " + memo.getNo();
			
			System.out.println(sql);
			
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			Statement stmt = conn.createStatement();
			// 2.3 delete 쿼리를 실행
			stmt.execute(sql);	
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				// 3. 데이터베이스 연결해제
				conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * list의 memo 수정
	 * @param memo
	 */
	public void update(Memo memo){
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return;
				
		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " update memo set name = ?, context = ?, datetime =? where no = ?";
					
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 2.3 물음표에 값을 세팅
			pstmt.setString(1, memo.getName());
			pstmt.setString(2, memo.getContent());
			pstmt.setTimestamp(3, new Timestamp( System.currentTimeMillis()));
			pstmt.setInt(4, memo.getNo());
			// 2.4 쿼리를 실행
			pstmt.executeUpdate();
					
			// 3. 데이터베이스 연결해제
			conn.close();	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * list에서 no같은 memo 가져오기
	 * @param no
	 * @return
	 */
	public Memo getMemo(int no){
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return null;
		
		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " select * from memo where no =" + no;
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			Statement stmt = conn.createStatement();
			// 2.3 select한 결과값을 돌려받기 위해 쿼리를 실행
			ResultSet rs = stmt.executeQuery(sql);
			// 결과셋을 반복하면서 no
			while(rs.next()){
				Memo memo = new Memo(rs.getString("name"), rs.getString("content"));
				memo.setNo(rs.getInt("no"));
				memo.setDatetime(rs.getTimestamp("datetime").getTime());
				
				return memo;
			}	
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				// 3. 데이터베이스 연결해제
				conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return null;
	}
}
