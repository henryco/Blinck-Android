package net.henryco.blinck.util.form.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.henryco.sqlightning.reflect.annotations.column.Column;
import net.henryco.sqlightning.reflect.annotations.column.Id;
import net.henryco.sqlightning.reflect.annotations.column.Relation;
import net.henryco.sqlightning.reflect.annotations.repository.Entity;
import net.henryco.sqlightning.reflect.annotations.repository.Table;

/**
 * Created by HenryCo on 28/09/17.
 */
@Data @Entity @NoArgsConstructor
public class UserMediaForm {


	@Column @Id
	private Long id;


	@Column @Expose
	@Table @Relation
	@SerializedName("photo")
	private UserImages images;


	@Data @Entity @NoArgsConstructor
	public static final class UserImages {

		@Column @Id
		private Long id;

		@Column @Expose
		private String avatar;

		@Column @Expose
		@SerializedName("photos")
		private String images;
	}

}