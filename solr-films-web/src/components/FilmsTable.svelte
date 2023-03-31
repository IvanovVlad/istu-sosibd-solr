<script lang="ts">
	import type { ApiResponse } from '../types';
	import { useFormatter } from '../utils/useFormatter';

	const { formatDate } = useFormatter();
	export let films: ApiResponse['films'];
</script>

<table>
	<thead>
		<tr>
			<th scope="col">id</th>
			<th scope="col">name</th>
			<th scope="col" nowrap>directed by</th>
			<th scope="col">genre</th>
			<th scope="col">release</th>
		</tr>
	</thead>
	<tbody>
		{#each films as f (f.id)}
			<tr>
				<td style="max-width: 200px; word-wrap: break-word">{f.id}</td>
				<td>{f.name}</td>
				<td>{f.directed_by}</td>
				<td>{f.genre}</td>
				<td>{formatDate(f.initial_release_date)}</td>
			</tr>
		{/each}
	</tbody>
</table>
