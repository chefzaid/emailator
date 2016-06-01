
INSERT INTO email(id, subject, body, sender) VALUES(1, 'Test', 'Hello World', 'emailator.test@gmail.com');

INSERT INTO email_attachments_paths(email_id, attachments_paths) VALUES(1, '/tmp/test.txt');

INSERT INTO bulk_email(id, uuid, email_id) VALUES(1, 'azerty1234', 1);

INSERT INTO progress(id, state) VALUES(1, NULL);
INSERT INTO progress(id, state) VALUES(2, NULL);
INSERT INTO progress(id, state) VALUES(3, NULL);

INSERT INTO recipient(id, email_address, bulk_email_id, progress_id) VALUES(1, 'emailator.test1@mailinator.com', 1, 1);
INSERT INTO recipient(id, email_address, bulk_email_id, progress_id) VALUES(2, 'emailator.test2@mailinator.com', 1, 2);
INSERT INTO recipient(id, email_address, bulk_email_id, progress_id) VALUES(3, 'emailator.test3@mailinator.com', 1, 3);

INSERT INTO bulk_email_recipients(bulk_email_id, recipients_id) VALUES(1, 1);
INSERT INTO bulk_email_recipients(bulk_email_id, recipients_id) VALUES(1, 2);
INSERT INTO bulk_email_recipients(bulk_email_id, recipients_id) VALUES(1, 3);
