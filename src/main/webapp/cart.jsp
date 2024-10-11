<%@page import="com.dao.ProductDao"%>
<%@page import="com.bean.Product"%>
<%@page import="com.dao.CartDao"%>
<%@page import="com.bean.Cart"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
  <head>
  <style>
  
	  .scrollremove::-webkit-scrollbar {
	  display: none;
		}
  </style>
    <title>Winkel - Free Bootstrap 4 Template by Colorlib</title>
      </head>
  <body class="goto-here">
		    

    <section class="ftco-section ftco-cart" style="margin-top: -100px;">
			<div class="container">
				<div class="row">
    			<div class="col-md-12 ftco-animate">
    				<div class="cart-list scrollremove">
	    				<table class="table">
						    <thead class="thead-primary">
						      <tr class="text-center">
						        <th>&nbsp;</th>
						        <th>Image</th>
						        <th>Product</th>
						        <th>Price</th>
						        <th>Quantity</th>
						        <th>Total</th>
						      </tr>
						    </thead>
						    <tbody>
						    <%
						    	int subtotal = 0;
						    	List<Cart> list = CartDao.getCartByUser(u.getUid());
						    	for(Cart c : list){
						    		subtotal = subtotal + c.getTotal_price();
						    		Product p = ProductDao.getProduct(c.getPid());	
						    	
						    %>
						    
						      <tr class="text-center">
						        <td class="product-remove"><a href="remove-from-cart.jsp?pid=<%=p.getPid() %>"><span class="ion-ios-close"></span></a></td>
						        
						        <td class="image-prod"><div class="img" style="background-image:url('product_picture/<%=p.getProduct_image()%>');"></div></td>
						        
						        <td class="product-name">
						        	<h3><%=p.getProduct_name() %></h3>
						        	<p><%=p.getProduct_desc() %></p>
						        </td>
						        
						        <td class="price">&#8377;<%=p.getProduct_price() %></td>
						        
						        <td class="quantity">
						        	<form method="post" action="change_qty.jsp">
							        	<div class="input-group mb-3">
							        		<input type="hidden" name="cid" value="<%=c.getCid()%>">
						             		<input type="number" name="product_qty" class="quantity form-control input-number" value="<%=c.getProduct_qty() %>" min="1" max="10" onchange="this.form.submit()">
						             	
						          		</div>
					          		</form>
					          </td>
						        
						        <td class="total">&#8377;<%=c.getTotal_price() %></td>
						      </tr><!-- END TR-->
								
							<%
						    	}
							%>
						      
						    </tbody>
						    
						  </table>
					  </div>
    			</div>
    		</div>
    		
    		<%
    			if(subtotal>0){	
    		%>
    		
    		<div class="row justify-content-center">
    			<div class="col col-lg-5 col-md-6 mt-5 cart-wrap ftco-animate">
    				<div class="cart-total mb-3">
    					<h3>Cart Totals</h3>
    					<p class="d-flex">
    						<span>Subtotal</span>
    						<span>&#8377;<%=subtotal %></span>
    					</p>
    					<p class="d-flex">
    						<span>Delivery</span>
    						<span>&#8377;0.00</span>
    					</p>
    					<p class="d-flex">
    						<span>Discount</span>
    						<span>&#8377;0.00</span>
    					</p>
    					<hr>
    					<p class="d-flex total-price">
    						<span>Total</span>
    						<span>&#8377;<%=subtotal %></span>
    					</p>
    				</div>
    				
    				<form>
    					<input type="hidden" id="amount1" value="<%=subtotal %>">
    				</form>
    				<p class="text-center">
    				<a href="checkout.jsp" class="btn btn-primary py-3 px-4">Proceed to Checkout</a>
    				<br>
    				<a href="#" id="payButton" onclick="CreateOrderID()" class="btn btn-primary py-3 px-4">Pay now</a>
    				</p>
    			</div>
    		</div>
    		<%
    			}
    			else{
    				
    		%>
    		<h2>No products in cart</h2>
    		<%
    			}
    		%>
			</div>
		</section>

    <footer class="ftco-footer bg-light ftco-section">
      <div class="container">
      	<div class="row">
      		<div class="mouse">
						<a href="#" class="mouse-icon">
							<div class="mouse-wheel"><span class="ion-ios-arrow-up"></span></div>
						</a>
					</div>
      	</div>
        <div class="row mb-5">
          <div class="col-md">
            <div class="ftco-footer-widget mb-4">
              <h2 class="ftco-heading-2">Winkel</h2>
              <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia.</p>
              <ul class="ftco-footer-social list-unstyled float-md-left float-lft mt-5">
                <li class="ftco-animate"><a href="#"><span class="icon-twitter"></span></a></li>
                <li class="ftco-animate"><a href="#"><span class="icon-facebook"></span></a></li>
                <li class="ftco-animate"><a href="#"><span class="icon-instagram"></span></a></li>
              </ul>
            </div>
          </div>
          <div class="col-md">
            <div class="ftco-footer-widget mb-4 ml-md-5">
              <h2 class="ftco-heading-2">Menu</h2>
              <ul class="list-unstyled">
                <li><a href="#" class="py-2 d-block">Shop</a></li>
                <li><a href="#" class="py-2 d-block">About</a></li>
                <li><a href="#" class="py-2 d-block">Journal</a></li>
                <li><a href="#" class="py-2 d-block">Contact Us</a></li>
              </ul>
            </div>
          </div>
          <div class="col-md-4">
             <div class="ftco-footer-widget mb-4">
              <h2 class="ftco-heading-2">Help</h2>
              <div class="d-flex">
	              <ul class="list-unstyled mr-l-5 pr-l-3 mr-4">
	                <li><a href="#" class="py-2 d-block">Shipping Information</a></li>
	                <li><a href="#" class="py-2 d-block">Returns &amp; Exchange</a></li>
	                <li><a href="#" class="py-2 d-block">Terms &amp; Conditions</a></li>
	                <li><a href="#" class="py-2 d-block">Privacy Policy</a></li>
	              </ul>
	              <ul class="list-unstyled">
	                <li><a href="#" class="py-2 d-block">FAQs</a></li>
	                <li><a href="#" class="py-2 d-block">Contact</a></li>
	              </ul>
	            </div>
            </div>
          </div>
          <div class="col-md">
            <div class="ftco-footer-widget mb-4">
            	<h2 class="ftco-heading-2">Have a Questions?</h2>
            	<div class="block-23 mb-3">
	              <ul>
	                <li><span class="icon icon-map-marker"></span><span class="text">203 Fake St. Mountain View, San Francisco, California, USA</span></li>
	                <li><a href="#"><span class="icon icon-phone"></span><span class="text">+2 392 3929 210</span></a></li>
	                <li><a href="#"><span class="icon icon-envelope"></span><span class="text">info@yourdomain.com</span></a></li>
	              </ul>
	            </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12 text-center">

            <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						  Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart color-danger" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
						  <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						</p>
          </div>
        </div>
      </div>
    </footer>
    
  

  <!-- loader -->
  <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


  <script src="js/jquery.min.js"></script>
  <script src="js/jquery-migrate-3.0.1.min.js"></script>
  <script src="js/popper.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/jquery.easing.1.3.js"></script>
  <script src="js/jquery.waypoints.min.js"></script>
  <script src="js/jquery.stellar.min.js"></script>
  <script src="js/owl.carousel.min.js"></script>
  <script src="js/jquery.magnific-popup.min.js"></script>
  <script src="js/aos.js"></script>
  <script src="js/jquery.animateNumber.min.js"></script>
  <script src="js/bootstrap-datepicker.js"></script>
  <script src="js/scrollax.min.js"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
  <script src="js/google-map.js"></script>
  <script src="js/main.js"></script>

	


  <script>
		$(document).ready(function(){

		var quantitiy=0;
		   $('.quantity-right-plus').click(function(e){
		        
		        // Stop acting like a button
		        e.preventDefault();
		        // Get the field name
		        var quantity = parseInt($('#quantity').val());
		        
		        // If is not undefined
		            
		            $('#quantity').val(quantity + 1);

		          
		            // Increment
		        
		    });

		     $('.quantity-left-minus').click(function(e){
		        // Stop acting like a button
		        e.preventDefault();
		        // Get the field name
		        var quantity = parseInt($('#quantity').val());
		        
		        // If is not undefined
		      
		            // Increment
		            if(quantity>0){
		            $('#quantity').val(quantity - 1);
		            }
		    });
		    
		});
	</script>
    
    <script type="text/javascript">
	var xhttp=new XMLHttpRequest();
	var RazorpayOrderId;
	function CreateOrderID()
	{
		xhttp.open("GET","http://localhost:8080/Winkel/OrderCreation?amount=<%=subtotal%>",false);
		xhttp.send();
		RazorpayOrderId=xhttp.responseText;
	//	alert(RazorpayOrderId);
	//	alert("Hello");
		OpenCheckOut();
	}
</script>
	<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
	<script type="text/javascript">
	
	
	function OpenCheckOut()
	{
	//	alert("Hello");
		var amount=document.getElementById("amount1").value;
		var new_amount=parseInt(amount)*100;
	//	alert(new_amount);
	//	alert("Hello");
		var options={
				"key":"rzp_test_X1iIiNlM3wnoAM",
				"amount":new_amount,
				"currency":"INR",
				"name":"Winkel",
				"description":"Test",
				"callback_url":"http://localhost:8080/Winkel/update_payment.jsp?uid=<%=u.getUid()%>",
				"prefill" : {
					"name" : "Sourav nebhnani",
					"email" : "Sourav.winkel@gmail.com",
					"contact" : "7726025429"
				},
				"notes" : {
					"address" : "Chittorgarh"
				},
				"theme" : {
					"color" : "#3399cc"
				}

			};
			var rzp1 = new Razorpay(options);
			rzp1.on('payment.failed', function(response) {
				alert(response.error.code);
				alert(response.error.description);
				alert(response.error.source);
				alert(response.error.step);
				alert(response.error.reason);
				alert(response.error.metadata.order_id);
				alert(response.error.metadata.payment_id);
			});
			rzp1.open();
			e.preventDefault();
		}
	</script>
    
    
  </body>
</html>