package com.demo;

import com.demo.dto.UserDemo1DTO;
import com.demo.dto.UserDemo2DTO;
import com.demo.dto.UserDemo3DTO;
import com.demo.dto.UserDemo8DTO;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private UserRepository userRepository;
/*	"total": 5,
			"max_score": 1,
			"hits": [
	{
		"_index": "test-v1",
			"_type": "user",
			"_id": "5",
			"_score": 1,
			"_source": {
				"sex": 2,
				"name": "testSave",
				"age": 100
	}
	},
	{
		"_index": "test-v1",
			"_type": "user",
			"_id": "2",
			"_score": 1,
			"_source": {
				"sex": 2,
				"name": "kobe",
				"age": 27
	}
	},
	{
		"_index": "test-v1",
			"_type": "user",
			"_id": "4",
			"_score": 1,
			"_source": {
				"sex": 2,
				"name": "marry",
				"age": 24
	}
	},
	{
		"_index": "test-v1",
			"_type": "user",
			"_id": "1",
			"_score": 1,
			"_source": {
				"sex": 1,
				"name": "tom",
				"age": 23
	}
	},
	{
		"_index": "test-v1",
			"_type": "user",
			"_id": "3",
			"_score": 1,
			"_source": {
				"name": "marry",
				"age": 10,
				"sex": 1
	}
	}
    ]*/
	// 就两个name和age字段



	//demo1 查询出age>22的 并且 性别是2的 user
    @Test
	public void demo1(){
		UserDemo1DTO userDemo1DTO = new UserDemo1DTO();
		userDemo1DTO.setAge(22);
		userDemo1DTO.setSex(2);
		System.out.println(userRepository.ageGt22(userDemo1DTO));
	}

	//demo2 列出性别1 ，2 的数量
	@Test
	public void demo2(){
		List<UserDemo2DTO> userDemo2DTOS = userRepository.countGroupBySex();
		System.out.println(userDemo2DTOS);
	}

	//求出每个性别的平均年龄
	@Test
	public void demo3(){
		List<UserDemo3DTO> userDemo3DTOS = userRepository.avgAgeGroupBySex();
		System.out.println(userDemo3DTOS);
	}

	@Test
	public void demo4(){
		List<User> users = userRepository.findAll();
		System.out.println(users);
	}

	@Test
	public void demo5(){
    	User user = new User();
    	user.setUserId(5L);
    	user.setName("testSave");
    	user.setSex(2);
    	user.setAge(100);
    	User result= userRepository.save(user);
		System.out.println(result);
	}

	@Test
	public void demo6(){
		User result= userRepository.findOne(1);
		System.out.println(result);
	}

	@Test
	public void demo7(){
    	List<User> users = new ArrayList<>();
		User user = new User();
		user.setUserId(6L);
		user.setName("bulkSave1");
		user.setSex(1);
		user.setAge(99);
		User user1 = new User();
		user1.setUserId(7L);
		user1.setName("bulkSave2");
		user1.setSex(2);
		user1.setAge(98);
		users.add(user);
		users.add(user1);
		userRepository.bulkSave(users);
	}

	//求出平均年龄,以及年龄的总和
	@Test
	public void demo8(){
		UserDemo8DTO userDemo8DTO = userRepository.avgAndTotalAge();
		System.out.println(userDemo8DTO);
	}
}
