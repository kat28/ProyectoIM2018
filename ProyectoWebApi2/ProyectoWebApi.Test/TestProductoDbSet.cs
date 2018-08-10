using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ProyectoWebApi2.Datos;

namespace ProyectoWebApi.Test
{
    public class TestProductoDbSet : TestDbSet<producto>
    {
        public override producto Find(params object[] keyValues)
        {
            return this.SingleOrDefault(producto => producto.IdProducto == (int)keyValues.Single());
        }
    }
}
