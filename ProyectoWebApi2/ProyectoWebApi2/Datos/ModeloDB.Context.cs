using System;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using ProyectoWebApi2.Models;

namespace ProyectoWebApi2.Datos
{
    
    public class proyectoDBEntities : DbContext, IStoreAppContext
    {
        public proyectoDBEntities()  : base("name=proyectoDBEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public virtual DbSet<producto> producto { get; set; }
        public virtual DbSet<proveedor> proveedors { get; set; }

        public void MarkAsModified(producto item)
        {
            Entry(item).State = EntityState.Modified;
        }

    }
}
