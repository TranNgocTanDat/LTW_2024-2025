package dao;

import context.DbContext;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    //add new product
    public void addProduct(Product product){
        String query ="INSERT INTO product (name, description, price, category, size, color, stockQuantity, imageUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getCategory());
            ps.setString(5, product.getSize());
            ps.setString(6, product.getColor());
            ps.setInt(7, product.getStockQuantity());
            ps.setString(8, product.getImageUrl());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //update product
    public void updateProduct(Product product) {
        String query = "UPDATE Product SET name=?, description=?, price=?, category=?, size=?, color=?, stockQuantity=?, imageUrl=? WHERE productId=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getCategory());
            ps.setString(5, product.getSize());
            ps.setString(6, product.getColor());
            ps.setInt(7, product.getStockQuantity());
            ps.setString(8, product.getImageUrl());
            ps.setInt(9, product.getProductId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Đóng các đối tượng để tránh rò rỉ tài nguyên
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //delete product
    public void deleteProduct(int productId) {
        String query = "DELETE FROM Product WHERE productID=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //get all product
    public List<Product> getAll(){
        List<Product> list = new ArrayList<>();
        String query ="select*from Product";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //get product by id
    public Product getProductById(int productId){
        Product product = null;
        String query = "SELECT * FROM Product WHERE productID=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()){
                product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice((float) rs.getDouble("price"));
                product.setCategory(rs.getString("category"));
                product.setSize(rs.getString("size"));
                product.setColor(rs.getString("color"));
                product.setStockQuantity(rs.getInt("stockQuantity"));
                product.setImageUrl(rs.getString("imageUrl"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
    public List<Product> getProductsByCategory(String category) {
        String query ="SELECT * FROM Product WHERE category = ?";
        List<Product> products = new ArrayList<>();
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, category);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String size = rs.getString("size");
                String color = rs.getString("color");
                int stockQuantity = rs.getInt("stockQuantity");
                String imageUrl = rs.getString("imageUrl");
                products.add(new Product(productId, name,description, price, category, size, color, stockQuantity, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public List<Product> searchProductByKeyWord(String keyword) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE name LIKE ? OR description LIKE ?";

        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);

            // Tạo giá trị cho tham số
            String q = "%" + keyword + "%";
            ps.setString(1, q);
            ps.setString(2, q);

            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                String size = rs.getString("size");
                String color = rs.getString("color");
                int stockQuantity = rs.getInt("stockQuantity");
                String imageUrl = rs.getString("imageUrl");
                products.add(new Product(productId, name, description, price, category, size, color, stockQuantity, imageUrl));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ProductDao productDao = new ProductDao();
//        productDao.getProductsByCategory("d");
//        List<Product> products = productDao.searchProductByKeyWord("Áo");
        List<Product> products = productDao.getAll();
        for (Product product: products) {
            System.out.println(product);
        }
    }
}
