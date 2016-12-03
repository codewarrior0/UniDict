package wanion.unidict.recipe;

/*
 * Created by WanionCane(https://github.com/WanionCane).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.core.recipe.AdvRecipe;
import ic2.core.recipe.AdvShapelessRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import wanion.lib.common.MetaItem;
import wanion.lib.recipe.RecipeHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IC2RecipeResearcher extends AbstractRecipeResearcher<AdvRecipe, AdvShapelessRecipe>
{
	@Override
	public int getShapedRecipeKey(@Nonnull AdvRecipe recipe)
	{
		final TIntList recipeKeys = new TIntArrayList();
		int recipeKey = 0;
		List<ItemStack> bufferInput;
		for (final IRecipeInput input : recipe.input)
			if (!(bufferInput = input.getInputs()).isEmpty())
				recipeKeys.add(MetaItem.get(resourceHandler.getMainItemStack(bufferInput.get(0))));
		recipeKeys.sort();
		for (final TIntIterator recipeKeysIterator = recipeKeys.iterator(); recipeKeysIterator.hasNext(); )
			recipeKey += 31 * recipeKeysIterator.next();
		return recipeKey;
	}

	@Override
	public int getShapelessRecipeKey(@Nonnull AdvShapelessRecipe recipe)
	{
		final TIntList recipeKeys = new TIntArrayList();
		int recipeKey = 0;
		List<ItemStack> bufferInput;
		for (final IRecipeInput input : recipe.input)
			if (!(bufferInput = input.getInputs()).isEmpty())
				recipeKeys.add(MetaItem.get(resourceHandler.getMainItemStack(bufferInput.get(0))));
		recipeKeys.sort();
		for (final TIntIterator recipeKeysIterator = recipeKeys.iterator(); recipeKeysIterator.hasNext(); )
			recipeKey += 31 * recipeKeysIterator.next();
		return recipeKey;
	}

	@Override
	@Nonnull
	public List<Class<? extends AdvRecipe>> getShapedRecipeClasses()
	{
		return Collections.singletonList(AdvRecipe.class);
	}

	@Override
	@Nonnull
	public List<Class<? extends AdvShapelessRecipe>> getShapelessRecipeClasses()
	{
		return Collections.singletonList(AdvShapelessRecipe.class);
	}

	@Override
	public ShapedOreRecipe getNewShapedRecipe(@Nonnull final AdvRecipe recipe)
	{
		final Object[] newRecipeInputs = new Object[9];
		final IRecipeInput[] recipeInputs = recipe.input;
		for (int i = 0; i < recipeInputs.length; i++) {
			final IRecipeInput input = recipeInputs[i];
			String oreName = input instanceof RecipeInputOreDict ? ((RecipeInputOreDict) input).input : null;
			if (oreName == null) {
				final boolean notEmpty = !input.getInputs().isEmpty();
				oreName = notEmpty ? uniOreDictionary.getName(input.getInputs().get(0)) : null;
				if (oreName != null)
					newRecipeInputs[i] = oreName;
				else if (notEmpty)
					newRecipeInputs[i] = input.getInputs().get(0);
			} else
				newRecipeInputs[i] = oreName;
		}
		return new ShapedOreRecipe(resourceHandler.getMainItemStack(recipe.getRecipeOutput()), RecipeHelper.rawShapeToShape(newRecipeInputs));
	}

	@Override
	public ShapedOreRecipe getNewShapedFromShapelessRecipe(@Nonnull final AdvShapelessRecipe recipe)
	{
		final Object[] newRecipeInputs = new Object[9];
		final IRecipeInput[] recipeInputs = recipe.input;
		for (int i = 0; i < recipeInputs.length; i++) {
			final IRecipeInput input = recipeInputs[i];
			String oreName = input instanceof RecipeInputOreDict ? ((RecipeInputOreDict) input).input : null;
			if (oreName == null) {
				final boolean notEmpty = !input.getInputs().isEmpty();
				oreName = notEmpty ? uniOreDictionary.getName(input.getInputs().get(0)) : null;
				if (oreName != null)
					newRecipeInputs[i] = oreName;
				else if (notEmpty)
					newRecipeInputs[i] = input.getInputs().get(0);
			} else
				newRecipeInputs[i] = oreName;
		}
		return new ShapedOreRecipe(resourceHandler.getMainItemStack(recipe.getRecipeOutput()), RecipeHelper.rawShapeToShape(newRecipeInputs));
	}

	@Override
	public ShapelessOreRecipe getNewShapelessRecipe(@Nonnull final AdvShapelessRecipe recipe)
	{
		final List<Object> inputs = new ArrayList<>();
		for (final IRecipeInput recipeInput : recipe.input) {
			String oreName = recipeInput instanceof RecipeInputOreDict ? ((RecipeInputOreDict) recipeInput).input : null;
			if (oreName == null) {
				final boolean notEmpty = !recipeInput.getInputs().isEmpty();
				oreName = notEmpty ? uniOreDictionary.getName(recipeInput.getInputs().get(0)) : null;
				if (oreName != null)
					inputs.add(oreName);
				else if (notEmpty)
					inputs.add(recipeInput.getInputs().get(0));
			} else
				inputs.add(oreName);
		}
		return new ShapelessOreRecipe(resourceHandler.getMainItemStack(recipe.getRecipeOutput()), inputs.toArray());
	}

	@Override
	public ShapelessOreRecipe getNewShapelessFromShapedRecipe(@Nonnull final AdvRecipe recipe)
	{
		final List<Object> inputs = new ArrayList<>();
		for (final IRecipeInput recipeInput : recipe.input) {
			String oreName = recipeInput instanceof RecipeInputOreDict ? ((RecipeInputOreDict) recipeInput).input : null;
			if (oreName == null) {
				final boolean notEmpty = !recipeInput.getInputs().isEmpty();
				oreName = notEmpty ? uniOreDictionary.getName(recipeInput.getInputs().get(0)) : null;
				if (oreName != null)
					inputs.add(oreName);
				else if (notEmpty)
					inputs.add(recipeInput.getInputs().get(0));
			} else
				inputs.add(oreName);
		}
		return new ShapelessOreRecipe(resourceHandler.getMainItemStack(recipe.getRecipeOutput()), inputs.toArray());
	}
}