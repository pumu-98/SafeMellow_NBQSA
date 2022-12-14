/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lk.parinda.safemellow.keylog.latin.utils;

import lk.parinda.safemellow.keylog.latin.settings.AdvancedSettingsFragment;
import lk.parinda.safemellow.keylog.latin.settings.AppearanceSettingsFragment;
import lk.parinda.safemellow.keylog.latin.settings.CustomInputStyleSettingsFragment;
import lk.parinda.safemellow.keylog.latin.settings.PreferencesSettingsFragment;
import lk.parinda.safemellow.keylog.latin.settings.SettingsFragment;
import lk.parinda.safemellow.keylog.latin.settings.ThemeSettingsFragment;

import java.util.HashSet;

public class FragmentUtils {
    private static final HashSet<String> sLatinImeFragments = new HashSet<>();
    static {
        sLatinImeFragments.add(PreferencesSettingsFragment.class.getName());
        sLatinImeFragments.add(AppearanceSettingsFragment.class.getName());
        sLatinImeFragments.add(ThemeSettingsFragment.class.getName());
        sLatinImeFragments.add(CustomInputStyleSettingsFragment.class.getName());
        sLatinImeFragments.add(AdvancedSettingsFragment.class.getName());
        sLatinImeFragments.add(SettingsFragment.class.getName());
    }

    public static boolean isValidFragment(String fragmentName) {
        return sLatinImeFragments.contains(fragmentName);
    }
}
