package annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Result {
	public String property();
	public String column();
	public boolean id()default false;
}
