CREATE INDEX idx_visitors_tag
    ON visitors (tag);

CREATE INDEX idx_visitors_tag_status
    ON visitors (tag, status);