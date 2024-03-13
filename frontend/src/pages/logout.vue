<script setup>

const socket = inject('socket')

$cookies.remove('jwtToken');

window.location.href = 'http://localhost:5173/login'


</script>
