namespace ProyectoWebApi2.Datos
{
    using System;
    using System.Collections.Generic;
    
    public partial class producto
    {
        public int IdProducto { get; set; }
        public string Nombre { get; set; }
        public decimal Precio { get; set; }
        public int Cantidad { get; set; }
    }
}
