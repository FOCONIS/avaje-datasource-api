package org.avaje.datasource;

import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class DataSourceConfigTest {

	@Test
	public void parseCustom() {

		DataSourceConfig config = new DataSourceConfig();
		Map<String, String> map = config.parseCustom("a=1;b=2;c=3");

		assertThat(map).hasSize(3);
		assertThat(map.get("a")).isEqualTo("1");
		assertThat(map.get("b")).isEqualTo("2");
		assertThat(map.get("c")).isEqualTo("3");
	}

	@Test
	public void isEmpty() {
		DataSourceConfig config = new DataSourceConfig();
		assertThat(config.isEmpty()).isTrue();

		config.setUrl("foo");
		assertThat(config.isEmpty()).isFalse();

		config = new DataSourceConfig();
		config.setUsername("foo");
		assertThat(config.isEmpty()).isFalse();

		config = new DataSourceConfig();
		config.setPassword("foo");
		assertThat(config.isEmpty()).isFalse();

		config = new DataSourceConfig();
		config.setDriver("foo");
		assertThat(config.isEmpty()).isFalse();
	}

	@Test
	public void defaults() {

		DataSourceConfig config = create();

		DataSourceConfig readOnly = new DataSourceConfig();
		readOnly.setDefaults(config);

		assertThat(readOnly.getDriver()).isEqualTo(config.getDriver());
		assertThat(readOnly.getUrl()).isEqualTo(config.getUrl());
		assertThat(readOnly.getUsername()).isEqualTo(config.getUsername());
		assertThat(readOnly.getPassword()).isEqualTo(config.getPassword());
	}

	@Test
	public void defaults_someOverride() {

		DataSourceConfig readOnly = new DataSourceConfig();
		readOnly.setUsername("foo2");
		readOnly.setUrl("jdbc:postgresql://127.0.0.2:5432/unit");

		DataSourceConfig config = create();
		readOnly.setDefaults(config);

		assertThat(readOnly.getPassword()).isEqualTo(config.getPassword());
		assertThat(readOnly.getDriver()).isEqualTo(config.getDriver());
		assertThat(readOnly.getUrl()).isEqualTo("jdbc:postgresql://127.0.0.2:5432/unit");
		assertThat(readOnly.getUsername()).isEqualTo("foo2");

	}

	private DataSourceConfig create() {
		DataSourceConfig config = new DataSourceConfig();
		config.setDriver("org.postgresql.Driver");
		config.setUrl("jdbc:postgresql://127.0.0.1:5432/unit");
		config.setUsername("foo");
		config.setPassword("bar");
		return config;
	}
}