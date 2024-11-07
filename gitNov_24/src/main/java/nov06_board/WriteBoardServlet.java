package nov06_board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WriteBoardServlet
 */
@WebServlet("/writeBoard.do")
public class WriteBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteBoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("EUC-KR");
		String title= request.getParameter("TITLE");
		String content = request.getParameter("CONTENT");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("USER");
		BoardDTO dto = new BoardDTO();//DTO 생성
		
		dto.setWriter(id);
		dto.setTitle(title);
		dto.setContent(content);
		
		
		WriteInsertDAO dao = new WriteInsertDAO(); //DAO를 생성한다.
		boolean flag = dao.doit(dto); //DAO에 게시글 정보가 들어있는 DTO를 넘긴다.
		
		if(flag)  { //게시글 등록이 성공한 경우
			System.out.println("게시글 등록 성공!"); //게시글 목록을 출력해야함 --> 게시글을 조회해서 출력하는 서블릿 새로 작성 => BoardListServlet
			response.sendRedirect("boardList.do");
		}
		else System.out.println("게시글 등록 실패!");
	}
}
class WriteInsertDAO {
	Connection conn;
	PreparedStatement pstmt;
	String query = "insert into free_board values(?,?,?,sysdate)";
	
	boolean doit(BoardDTO dto) {
		boolean yesOrNo = false;
		
		try {
			Class.forName(OracleXE11g.LIB);
			conn = DriverManager.getConnection(OracleXE11g.NAME, "hr", "hr");
			pstmt = conn.prepareStatement(query);
			// 삽입만 하기때문에 쿼리문을 실행하고 결과를 담는 ResultSet은 사용안함
			
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.executeUpdate(); //insert 를 실행한다, 삽입삭제변경 모두 같은 메서드사용함(insert,delete,update)
			yesOrNo = true;
			
		} catch(Exception e) {
			System.out.println("게시글 삽입 중 문제 발생!");
		} finally {
			try { conn.close(); } catch(Exception e) {}
		}
		
		return yesOrNo;
	}
}

class OracleXE11g {
	static final String LIB = "oracle.jdbc.driver.OracleDriver";//상수
	static final String NAME = "jdbc:oracle:thin:@localhost:1521:XE";//상수
}
