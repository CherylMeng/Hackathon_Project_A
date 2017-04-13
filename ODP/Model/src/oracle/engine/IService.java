package oracle.engine;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IService {
	public void execute(Context context) throws JSONException, IOException;
}
