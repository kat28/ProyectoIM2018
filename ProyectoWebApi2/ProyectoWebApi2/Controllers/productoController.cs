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

namespace ProyectoWebApi2.Controllers
{
    //[EnableCors(origins: "http://proyecto2018kt.azurewebsites.net", headers: "*", methods: "*")]
    [EnableCors(origins:"*",headers:"*",methods:"*")]
    public class productoController : ApiController
    {
        private proyectoDBEntities db = new proyectoDBEntities();

        // GET: api/producto
        public IQueryable<producto> Getproductos()
        {
            return db.productos;
        }

        // GET: api/producto/5
        [ResponseType(typeof(producto))]
        public async Task<IHttpActionResult> Getproducto(int id)
        {
            producto producto = await db.productos.FindAsync(id);
            if (producto == null)
            {
                return NotFound();
            }

            return Ok(producto);
        }

        // PUT: api/producto/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> Putproducto(int id, producto producto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != producto.IdProducto)
            {
                return BadRequest();
            }

            db.Entry(producto).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
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
        public async Task<IHttpActionResult> Postproducto(producto producto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.productos.Add(producto);

            try
            {
                await db.SaveChangesAsync();
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
        public async Task<IHttpActionResult> Deleteproducto(int id)
        {
            producto producto = await db.productos.FindAsync(id);
            if (producto == null)
            {
                return NotFound();
            }

            db.productos.Remove(producto);
            await db.SaveChangesAsync();

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
            return db.productos.Count(e => e.IdProducto == id) > 0;
        }
    }
}