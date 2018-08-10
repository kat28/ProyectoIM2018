using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ProyectoWebApi2;

namespace UnitTestProjectoApi
{
    /// <summary>
    /// Summary description for UnitTestProductController
    /// </summary>
    [TestClass]
    public class UnitTestProductController
    {
        [TestMethod]
        public void GetAllProducts_ShouldReturnAllProducts()
        {
            var testProducts = GetTestProducts();

            var testProductos = 


            var controller = new SimpleProductController(testProducts);

            var result = controller.GetAllProducts() as List<Product>;
            Assert.AreEqual(testProducts.Count, result.Count);
        }
    }
}
