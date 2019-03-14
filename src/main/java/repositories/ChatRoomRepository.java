
package repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ChatRoom;
import domain.StudentMessage;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

	@Query("select m from ChatRoom pg join pg.studentMessages m where pg.id=?1")
	ArrayList<StudentMessage> findGroupStudentMessages(int chatRoomId);
}
