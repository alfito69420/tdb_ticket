package ticket;

import java.sql.*;

public class ticket {

    //  VARIABLES CONEXIÓN
    private static Connection c = null;
    private static Statement stmt = null;
    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    //  VARIABLES AUTENTICACION
    private static String database = "ventas";
    private static String host = "localhost";
    private static String user = "gerente";
    private static String psswd = "123";
    private static final int port = 55432;

    //  VARIABLES SUCURSAL
    private static String tienda;
    private static String estado;
    private static String municipio;
    private static String localidad;
    private static String empleado;
    private static Date fecha;
    private static double monto;
    private static String cliente;

    //  VARIABLES TICKET
    private static double total;
    private static String unidadesDevueltas;
    private static double subDescontar = 0.0;
    private static String producto;
    private static String productoDevuelto;
    private static int cantidad;
    private static double subtotal;
    private static boolean devolucion;

    public static void main(String[] args) {
        conexionDB();
    }//close main

    private static Connection conexionDB() {
        try {
            Class.forName(JDBC_DRIVER);
            c = DriverManager.getConnection("jdbc:postgresql://"
                    + host + ":" + port + "/" + database, user, psswd);
            System.out.println("Conexión realizada con éxito\n");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from vt_datos();");

            while (rs.next()) {
                tienda = rs.getString("vt_tienda");
                estado = rs.getString("vt_estado");
                municipio = rs.getString("vt_municipio");
                localidad = rs.getString("vt_localidad");
                empleado = rs.getString("vt_empleado");
                fecha = rs.getDate("vt_fecha");
                monto = rs.getDouble("vt_monto");
                cliente = rs.getString("vt_cliente");

                total = +monto;
            }

            System.out.printf("%60s%59s", "SUCURSALTEC\n", "BIENVENIDO");

            System.out.println();
            System.out.printf("\nESTADO %84s \nMUNICIPIO %81s \nLOCALIDAD %82s",
                    estado,
                    municipio,
                    localidad);

            System.out.print("\n===========================================================" +
                    "===================================");
            System.out.printf("\nTIENDA %84s \nEMPLEADO %82s \nCLIENTE %82s\nFECHA COMPRA %78s",
                    tienda,
                    empleado,
                    cliente,
                    fecha);

            ResultSet rs2 = stmt.executeQuery("select * from vt_detalle();");
            System.out.println();
            System.out.println("===========================================================" +
                    "===================================");
            System.out.printf("%-30s %30s%30s", "PRODUCTO", "CANTIDAD", "SUBTOTAL");

            while (rs2.next()) {

                producto = rs2.getString("vt_producto");
                cantidad = rs2.getInt("vt_cantidad");
                subtotal = rs2.getDouble("vt_subtotal");
                devolucion = rs2.getBoolean("vt_devolucion");

                System.out.println();
                System.out.printf("%-30s %30s%30s", producto, cantidad, subtotal);

                if (devolucion == false) {
                    subDescontar = +subtotal;
                    productoDevuelto = producto;
                }
            }
            System.out.println();
            System.out.println("===========================================================" +
                    "===================================");
            System.out.printf("%-30s  %30s%30s", "DEVOLUCIONES", "CANTIDAD", "SUBTOTAL");
            unidadesDevueltas = "-" + cantidad;
            System.out.println();
            System.out.printf("%-30s %30s%30.2f", productoDevuelto, unidadesDevueltas, subtotal);

            System.out.println();
            System.out.println("===========================================================" +
                    "===================================");
            System.out.printf("%s    ", "MONTO TOTAL");
            System.out.printf("%77.2f", (total - subDescontar));

            rs.close();
            rs2.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }//close method
}//close method
