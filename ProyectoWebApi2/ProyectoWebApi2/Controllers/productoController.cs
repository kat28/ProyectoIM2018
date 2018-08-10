using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using System.Web.Http.Cors;
using ProyectoWebApi2.Datos;
using ProyectoWebApi2.Models;

namespace ProyectoWebApi2.Controllers
{
    
    [EnableCors(origins:"*",headers:"*",methods:"*")]
    public class productoController : ApiController
    {
        //private proyectoDBEntities db = new proyectoDBEntities();

        //agrega inyeccion de dependencias
        private IStoreAppContext db = new proyectoDBEntities();

        // add contructores
        public productoController() { }

        public productoController(IStoreAppContext context)
        {
            db = context;
        }


        // GET: api/producto
        public IQueryable<producto> Getproductos()
        {
            return db.producto;
        }

        // GET: api/producto/5
        [ResponseType(typeof(producto))]
        //public async Task<IHttpActionResult> Getproducto(int id)
        public IHttpActionResult Getproducto(int id)
        {
            //= await findAsync
            producto producto = db.producto.Find(id);
            if (producto == null)
            {
                return NotFound();
            }

            return Ok(producto);
        }

        // PUT: api/producto/5
        [ResponseType(typeof(void))]

        //public async Task<IHttpActionResult> Putproducto(producto producto, int id)
        public IHttpActionResult Putproducto(producto producto, int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != producto.IdProducto)
            {
                return BadRequest();
            }

            //db.Entry(producto).State = EntityState.Modified;
            db.MarkAsModified(producto);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!productoExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/producto
        [ResponseType(typeof(producto))]
        //public async Task<IHttpActionResult> Postproducto(producto producto)
        public IHttpActionResult Postproducto(producto producto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.producto.Add(producto);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (productoExists(producto.IdProducto))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = producto.IdProducto }, producto);
        }

        // DELETE: api/producto/5
        [ResponseType(typeof(producto))]
        public IHttpActionResult Deleteproducto(int id)
        //public async Task<IHttpActionResult> Deleteproducto(int id)
        {
            //= await FindAsync
            producto producto = db.producto.Find(id);
            if (producto == null)
            {
                return NotFound();
            }

            db.producto.Remove(producto);
            //await db.SaveChangesAsync();
            db.SaveChanges();

            return Ok(producto);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool productoExists(int id)
        {
            return db.producto.Count(e => e.IdProducto == id) > 0;
        }
    }
}