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
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class proveedorController : ApiController
    {
        private proyectoDBEntities db = new proyectoDBEntities();

        // GET: api/proveedor
        public IQueryable<proveedor> Getproveedors()
        {
            return db.proveedors;
        }

        // GET: api/proveedor/5
        [ResponseType(typeof(proveedor))]
        public async Task<IHttpActionResult> Getproveedor(int id)
        {
            proveedor proveedor = await db.proveedors.FindAsync(id);
            if (proveedor == null)
            {
                return NotFound();
            }

            return Ok(proveedor);
        }

        // PUT: api/proveedor/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> Putproveedor(int id, proveedor proveedor)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != proveedor.IdProveedor)
            {
                return BadRequest();
            }

            db.Entry(proveedor).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!proveedorExists(id))
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

        // POST: api/proveedor
        [ResponseType(typeof(proveedor))]
        public async Task<IHttpActionResult> Postproveedor(proveedor proveedor)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.proveedors.Add(proveedor);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (proveedorExists(proveedor.IdProveedor))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = proveedor.IdProveedor }, proveedor);
        }

        // DELETE: api/proveedor/5
        [ResponseType(typeof(proveedor))]
        public async Task<IHttpActionResult> Deleteproveedor(int id)
        {
            proveedor proveedor = await db.proveedors.FindAsync(id);
            if (proveedor == null)
            {
                return NotFound();
            }

            db.proveedors.Remove(proveedor);
            await db.SaveChangesAsync();

            return Ok(proveedor);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool proveedorExists(int id)
        {
            return db.proveedors.Count(e => e.IdProveedor == id) > 0;
        }
    }
}