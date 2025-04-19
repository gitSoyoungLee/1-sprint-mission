DROP TABLE IF EXISTS message_attachments;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS read_statuses;
DROP TABLE IF EXISTS channels;
DROP TABLE IF EXISTS user_statuses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS binary_contents;
DROP TYPE IF EXISTS channel_type;

CREATE TABLE binary_contents(
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  file_name VARCHAR(255) NOT NULL,
  size BIGINT NOT NULL,
  content_type VARCHAR(100) NOT NULL
);

CREATE TABLE users (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL,
  profile_id UUID,
  FOREIGN KEY (profile_id) REFERENCES binary_contents(id)
	  ON DELETE SET NULL
);

CREATE TYPE channel_type AS ENUM ('PUBLIC', 'PRIVATE');

CREATE TABLE channels (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  name VARCHAR(100),
  description VARCHAR(500),
  type channel_type NOT NULL
);

CREATE TABLE messages (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  content TEXT,
  channel_id UUID NOT NULL,
  author_id UUID,
  FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
  FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE user_statuses (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  user_id UUID UNIQUE NOT NULL,
  last_active_at TIMESTAMP WITH TIME ZONE NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE read_statuses (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  user_id UUID,
  channel_id UUID,
  last_read_at TIMESTAMP WITH TIME ZONE NOT NULL,
  UNIQUE (channel_id, user_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (channel_id ) REFERENCES channels(id) ON DELETE CASCADE
);

CREATE TABLE message_attachments (
 message_id UUID,
 attachment_id UUID,
 PRIMARY KEY (message_id, attachment_id),
 FOREIGN KEY(message_id) REFERENCES messages(id) ON DELETE CASCADE,
 FOREIGN KEY(attachment_id) REFERENCES binary_contents(id) ON DELETE CASCADE
);