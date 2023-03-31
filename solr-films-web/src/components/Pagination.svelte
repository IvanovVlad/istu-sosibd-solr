<script lang="ts">
	import { createEventDispatcher } from 'svelte';

	export let amount: number;
	export let currentPage: number;
	export let rows: number;

	$: pagesCount = Math.ceil(amount / rows);

	const dispatch = createEventDispatcher<{ pageChange: number }>();

	function nextPage() {
		if (currentPage < pagesCount) {
			dispatch('pageChange', currentPage + 1);
		}
	}

	function prevPage() {
		if (currentPage > 1) {
			dispatch('pageChange', currentPage - 1);
		}
	}
</script>

<div class="grid">
	<button on:click={prevPage}>prev</button>
	{#if pagesCount > 1}
		<button class="outline secondary">1</button>
		<button class="outline">{currentPage}</button>
		<button class="outline secondary">{pagesCount}</button>
	{:else}
		<button class="outline">{currentPage}</button>
	{/if}
	<button on:click={nextPage}>next</button>
</div>
