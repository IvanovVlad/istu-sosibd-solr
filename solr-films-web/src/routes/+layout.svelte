<script lang="ts">
	import '@picocss/pico';
	import '../app.css';
</script>

<header class="container">
	<h1>Simple films aggregator</h1>
	<nav>
		<ul>
			<li><a href="/">main page</a></li>
			<li><a href="/add">add film</a></li>
		</ul>
	</nav>
</header>
<slot />
