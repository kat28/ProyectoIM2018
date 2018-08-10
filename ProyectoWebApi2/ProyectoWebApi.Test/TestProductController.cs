using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Web.Http.Results;
using System.Net;
using ProyectoWebApi2.Models;
using ProyectoWebApi2.Datos;
using ProyectoWebApi2.Controllers;

namespace ProyectoWebApi.Test
{
    [TestClass]
    public class TestProductController
    {

        [TestMethod]
        public void PostProducto_DeberiaRetornarMismoProducto()
        {
            var controller = new productoController(new TestStoreAppContext());

            var item = GetDemoProduct();

            var result = controller.Postproducto(item) as CreatedAtRouteNegotiatedContentResult<producto>;


            Assert.IsNotNull(result);
            Assert.AreEqual(result.RouteName, "DefaultApi");
            Assert.AreEqual(result.RouteValues["id"], result.Content.IdProducto);
            Assert.AreEqual(result.Content.Nombre, item.Nombre);

        }

        [TestMethod]
        public void PutProducto_DebeRetornarStatus204()
        {
            var controller = new productoController(new TestStoreAppContext());

            var item = GetDemoProduct();


            var result = (controller.Putproducto(item, item.IdProducto)) as StatusCodeResult;
            
             
            Assert.IsNotNull(result);
            Assert.IsInstanceOfType(result, typeof(StatusCodeResult));
            Assert.AreEqual(HttpStatusCode.NoContent, result.StatusCode);
        }

        [TestMethod]
        public void PutProduct_DeberiaFallar_ConDiferenteID()
        {
            var controller = new productoController(new TestStoreAppContext());

            var badresult = controller.Putproducto(GetDemoProduct(), 999);
            Assert.IsInstanceOfType(badresult, typeof(BadRequestResult));
        }

        [TestMethod]
        public void GetProduct_DeberiaRetornarProductoMismoID()
        {
            var context = new TestStoreAppContext();
            context.producto.Add(GetDemoProduct());

            var controller = new productoController(context);
            var result = controller.Getproducto(3) as OkNegotiatedContentResult<producto>;

            Assert.IsNotNull(result);
            Assert.AreEqual(3, result.Content.IdProducto);
        }

        [TestMethod]
        public void DeleteProducto_DeberiaRetornarOK()
        {
            var context = new TestStoreAppContext();
            var item = GetDemoProduct();
            context.producto.Add(item);

            var controller = new productoController(context);
            var result = controller.Deleteproducto(3) as OkNegotiatedContentResult<producto>;

            Assert.IsNotNull(result);
            Assert.AreEqual(item.IdProducto, result.Content.IdProducto);
        }

        /// <summary>
        /// Gets all.
        /// </summary>
        [TestMethod]
        public void GetProducts_DebeRetornarTodosProductos()
        {
            var context = new TestStoreAppContext();
            context.producto.Add(new producto { IdProducto = 1, Nombre = "Prueba 1", Precio = 5, Cantidad = 10 });
            context.producto.Add(new producto { IdProducto = 2, Nombre = "Prueba 2", Precio = 10, Cantidad = 20 });
            context.producto.Add(new producto { IdProducto = 3, Nombre = "Prueba 3", Precio = 15, Cantidad = 30 });

            var controller = new productoController(context);
            var result = controller.Getproductos() as TestProductoDbSet;

            Assert.IsNotNull(result);
            Assert.AreEqual(3, result.Local.Count);
        }

        producto GetDemoProduct()
        {
            return new producto() { IdProducto = 3, Nombre = "Demo name", Precio = 5, Cantidad = 10 };
        }



    }
}
