document.addEventListener('DOMContentLoaded', function() {
    const quantityInputs = document.querySelectorAll('.update-form input[name="quantity"]');
    
    quantityInputs.forEach(input => {
        input.addEventListener('change', function() {
            const form = this.closest('.update-form');
            const productId = form.querySelector('input[name="productId"]').value;
            const quantity = this.value;
            const row = this.closest('tr');
            
            // Send AJAX request
            fetch('/cart/update', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `productId=${productId}&quantity=${quantity}`
            })
            .then(response => response.json())
            .then(data => {
                // Update item total price
                const totalPriceCell = row.querySelector('td:nth-child(4)');
                totalPriceCell.textContent = new Intl.NumberFormat('en-US', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2
                }).format(data.itemTotal);
                
                // Update cart total price
                const cartTotalCell = document.querySelector('tfoot td:nth-child(2)');
                cartTotalCell.textContent = new Intl.NumberFormat('en-US', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2
                }).format(data.cartTotal);
                
                // Update cart count in navbar
                const cartCount = document.querySelector('.badge');
                cartCount.textContent = data.cartCount;
            })
            .catch(error => {
                console.error('Error updating cart:', error);
            });
        });
    });
});
