<script lang="ts">
	import { enhance } from '$app/forms';
	import { page } from '$app/state';


	interface Props {
		parent: any;
		form: any;
	}

	let { parent, form }: Props = $props();

	const types = [{
			id: "321",
			name: "Black"
		},{
			id: "331",
			name: "White"
		}]

	// Base Classes
	const cBase = 'card p-4 w-modal shadow-xl space-y-4';
	const cHeader = 'text-2xl font-bold';
	const cForm = 'border border-surface-500 p-4 space-y-4 rounded-container-token';
</script>

<!-- {#if $modalStore[0]} -->
	<div class="modal-example-form {cBase}">
		<!-- <header class={cHeader}>{$modalStore[0].title ?? '(title missing)'}</header> -->
		<!-- <article>{$modalStore[0].body ?? '(body missing)'}</article> -->
		<!-- Enable for debugging: -->
		<form class="modal-form {cForm}" id="add_card" action="?/save" method="POST" use:enhance={() => {
			return async ({ update }) => {
				await update();
				// modalStore.close();
			}
		}}>
			<label class="label">
				<span>Content</span>
				<input class="input" type="text" value={form?.name ?? ''} name="content" placeholder="Enter text..." required/>
			</label>
			<label class="label">
				<span>Deck</span>
				<input class="input" type="text" value={$page.params.id ?? ''} name="deck_id" placeholder="Select deck..." required readonly>
			</label>
			<label class="label">
				<span>Type</span>
				<label for="underline_select" class="sr-only">Select card type...</label>
				<select id="underline_select" name="type_id" form="add_card" class="block py-2.5 px-2.5 text-md text-gray-500 input border-0 border-b-2 border-gray-200 dark:text-gray-400 dark:border-gray-700 focus:outline-none focus:ring-0 focus:border-gray-200" required>
					{#each $page.data.cardTypes as type}
						<option value="{type.id}">{type.name}</option>
					{/each}
				</select>
			</label>
			<!-- prettier-ignore -->
			<footer class="modal-footer {parent.regionFooter}">
				<button class="btn {parent.buttonNeutral}" onclick={parent.onClose}>{parent.buttonTextCancel}</button>
				<button class="btn {parent.buttonPositive}">Submit</button>
			</footer>
		</form>
	</div>
<!-- {/if} -->