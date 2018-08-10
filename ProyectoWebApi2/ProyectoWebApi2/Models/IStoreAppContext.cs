using System;
using System.Data.Entity;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ProyectoWebApi2.Datos;

namespace ProyectoWebApi2.Models
{ 
    public interface IStoreAppContext : IDisposable
    {
        DbSet<producto> producto { get; }

        int SaveChanges();

        void MarkAsModified(producto item);

    }
}
