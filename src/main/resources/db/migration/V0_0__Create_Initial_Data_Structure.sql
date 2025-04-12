CREATE TABLE IF NOT EXISTS deck(
    id      UUID        NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    name    VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS card_type(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS card(
    id           UUID          NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    content      VARCHAR(200),
    type_id      UUID,
    deck_id      UUID,
    prompts      INT         NOT NULL DEFAULT 0,

    CONSTRAINT deck_id_foreign_key FOREIGN KEY (deck_id) REFERENCES deck(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT card_type_foreign_key FOREIGN KEY (type_id) REFERENCES card_type(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS game(
    id          UUID        NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    game_state  VARCHAR(40) NOT NULL,
    deck_id     UUID
);

CREATE TABLE IF NOT EXISTS player(
    game_id UUID        NOT NULL,
    czar    BOOL        NOT NULL DEFAULT FALSE,
    score   INT         NOT NULL DEFAULT 0,
    email   VARCHAR(40) NOT NULL PRIMARY KEY,
    host    BOOL        NOT NULL DEFAULT FALSE,
    CONSTRAINT game_id_foreign_key FOREIGN KEY (game_id) REFERENCES game(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS game_card(
    id       UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    game_id  UUID NOT NULL,
    card_id  UUID NOT NULL,
    player_email VARCHAR(40),
    used   BOOL NOT NULL DEFAULT FALSE,
    used_at TIMESTAMP,
    response_order   INT NOT NULL DEFAULT 0,
    CONSTRAINT game_id_foreign_key FOREIGN KEY (game_id) REFERENCES game(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT card_id_foreign_key FOREIGN KEY (card_id) REFERENCES card(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS deck_name_index ON deck(name);
CREATE INDEX IF NOT EXISTS card_type_index ON card_type(name);
CREATE INDEX IF NOT EXISTS game_index ON game(id);