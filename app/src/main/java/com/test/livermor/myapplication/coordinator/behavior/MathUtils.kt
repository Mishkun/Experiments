package com.test.livermor.myapplication.coordinator.behavior

/**
 * Affine transform range [a, b] into range [c, d], saving relative relations
 *
 * @author dumchev on 12/10/2018.
 */
fun transformInRange(
        oldValue: Float,
        newMin: Float, newMax: Float,
        oldMin: Float = 0f, oldMax: Float = 1f
): Float {
    return newMin + ((oldValue - oldMin) * (newMax - newMin)) / (oldMax - oldMin)
}
