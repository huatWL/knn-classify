package zx.soft.kdd.music.recommender.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用户类
 * 
 * @author wanggang
 *
 */
public class Users implements Iterable<User> {

	private final Map<Integer, User> users;

	public Users() {
		users = new HashMap<Integer, User>();
	}

	protected void addUser(User user) {
		users.put(user.getID(), user);
	}

	public User getUser(int id) {
		return users.get(id);
	}

	public ArrayList<User> getUserList() {
		return (ArrayList<User>) users.values();
	}

	@Override
	public Iterator<User> iterator() {
		return users.values().iterator();
	}

}
