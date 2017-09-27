package net.henryco.blinck.util.form.user;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.henryco.sqlightning.reflect.annotations.column.Column;
import net.henryco.sqlightning.reflect.annotations.column.Id;
import net.henryco.sqlightning.reflect.annotations.column.Relation;
import net.henryco.sqlightning.reflect.annotations.repository.Entity;
import net.henryco.sqlightning.reflect.annotations.repository.Table;

/**
 * Created by HenryCo on 25/09/17.
 */
@Data @NoArgsConstructor @Entity
public final class UserProfileForm {

	private @Id
	@Column Long id;


	private @Expose @Relation @Column
	@Table UserNameForm userName;


	private @Expose
	@Column String gender;


	private @Expose
	@Column String about;


	private @Expose
	@Column Long birthday;

}
