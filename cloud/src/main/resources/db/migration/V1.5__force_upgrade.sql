create table if not exists app_version_details (
	serial_no serial PRIMARY KEY,
	current_version VARCHAR ( 50 ) UNIQUE NOT NULL,
	force_upgrade BOOLEAN NOT NULL,
	recommended_upgrade BOOLEAN NOT NULL
);