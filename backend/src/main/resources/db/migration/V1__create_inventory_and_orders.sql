CREATE TABLE products (
  id BIGSERIAL PRIMARY KEY,
  sku VARCHAR(40) NOT NULL CONSTRAINT uq_products_sku UNIQUE,
  name VARCHAR(150) NOT NULL,
  price NUMERIC(12,2) NOT NULL CHECK (price > 0),
  stock INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0)
);
CREATE TABLE purchase_orders (
  id BIGSERIAL PRIMARY KEY,
  product_id BIGINT NOT NULL REFERENCES products(id),
  quantity INTEGER NOT NULL CHECK (quantity > 0),
  unit_price NUMERIC(12,2) NOT NULL,
  total_price NUMERIC(12,2) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_purchase_orders_product ON purchase_orders(product_id);
CREATE INDEX idx_purchase_orders_created_at ON purchase_orders(created_at);
