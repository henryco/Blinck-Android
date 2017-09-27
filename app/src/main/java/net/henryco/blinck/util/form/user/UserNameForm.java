package net.henryco.blinck.util.form.user;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.henryco.sqlightning.reflect.annotations.column.Column;
import net.henryco.sqlightning.reflect.annotations.column.Id;
import net.henryco.sqlightning.reflect.annotations.repository.Entity;

/**
 * Created by HenryCo on 25/09/17.
 */
@Data @Entity
@NoArgsConstructor
public final class UserNameForm {


	private @Id
	@Column Long id;


	private @Expose @Column(
			value = "first_name"
	) String firstName;


	private @Expose @Column(
			value = "second_name"
	) String secondName;


	private @Expose @Column(
			value = "last_name"
	) String lastName;


	private @Expose @Column(
			value = "nickname"
	) String nickname;

}