INSERT INTO `user_roles` (`user_id`, `roles`)
VALUES (1, 'GUEST');

INSERT INTO `users` (`id`, `username`, `email`, `name`, `nickname`, `password`, `phone_number`, `updated_at`, `created_at`)
VALUES (1, 'iamguest', 'iamguest@chanmul.com', '게스트', '나는게스트',
        '{bcrypt}$2a$10$O0gSqLTyzKFjNEp9aBHuDu.tMZuPQWmvSqksGp7OpXmGix5YxwNNy', '01012345678', now(), now());
