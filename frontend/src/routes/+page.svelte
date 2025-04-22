<script lang='ts'>
	import { goto } from '$app/navigation';
	import { signIn, signOut } from '@auth/sveltekit/client';
	import type { PageProps } from './$types';
	let { data }: PageProps = $props();
	let newGameUrl = "/game/DRAFT"
</script>

<div class="container mt-12 mx-auto">
	<div class="grid grid-cols-1 place-content-stretch mx-96">
		{#await data}
			loading...
		{:then data}
			<h1 class="m-4 p-4 text-3xl flex justify-center">Main Menu</h1>
			{#if data.session}
				<button class="btn btn-primary bg-white border-black border-2 rounded-xl p-4 hover:boder-4 active:font-bold m-4" onclick={() => goto(newGameUrl)}>New Game</button>
				<button class="btn btn-primary bg-white border-black border-2 rounded-xl p-4 hover:boder-4 active:font-bold m-4" onclick={() => signOut()}>Log Out</button>
			{:else}
				<button class="btn btn-primary bg-white border-black border-2 rounded-xl p-4 hover:border-4 active:font-bold m-4" onclick={() => signIn("github")}>Log In With Github</button>
				<button class="btn btn-primary bg-white border-black border-2 rounded-xl p-4 hover:border-4 active:font-bold m-4" onclick={() => signIn("auth0")}>Log In With Email</button>
			{/if}
		{:catch error}
			{error}
		{/await}
	</div>
</div>