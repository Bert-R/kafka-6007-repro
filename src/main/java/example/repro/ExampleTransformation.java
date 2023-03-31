package example.repro;

import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.transforms.Transformation;

public class ExampleTransformation<R extends ConnectRecord<R>> implements Transformation<R>
{
	@Override
	public R apply(R rec)
	{
		System.out.println("ExampleTransformation.apply");
		return rec;
	}

	@Override
	public ConfigDef config()
	{
		System.out.println("ExampleTransformation.config");
		return new ConfigDef();
	}

	@Override
	public void close()
	{
		System.out.println("ExampleTransformation.close");
	}

	@Override
	public void configure(Map<String, ?> configs)
	{
		System.out.println("ExampleTransformation.configure");
	}
}
