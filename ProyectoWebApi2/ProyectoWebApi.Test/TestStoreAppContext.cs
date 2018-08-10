using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;
using ProyectoWebApi2.Models;
using ProyectoWebApi2.Datos;

namespace ProyectoWebApi.Test
{
    public class TestStoreAppContext : IStoreAppContext
    {
        

        public TestStoreAppContext()
        {
            this.producto = new TestProductoDbSet();

        }

        public DbSet<producto> producto { get; set; }

          public int SaveChanges()
        {
            return 0;
        }

        public void MarkAsModified(producto item) { }

        public void Dispose() { }
    }
}
