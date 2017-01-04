# famodulus-server API Description

famodulus-server provides a simple, RESTful API which is exposed at `/api` and which accepts and returns JSON.
In the following, all exposed methods are described.

## /api/modexp

This method calculates one or multiple given modexps (modular exponentiations, b^e mod m) using `java.math.BigInteger.modPow()`.
It only supports HTTP POST requestst and expects a JSON object in the following form:

```
    { "brief": boolean,
      "b": "default base", 
      "e": "default exponent", 
      "m": "default modulus", 
      "modexps": [ modexp1, modexp2, ... ]}
```

`brief` is a boolean option telling the server to either only return the calculated results (true) or to return the results
including the original modexp parameters (false). It can be omitted, in which case it defaults to true.

`"b"`, `"e"` and `"m"` are default values for the enclosed modexps; they are applied if any of the enclosed modexps misses one
or more values. For instance, if all modexps use the same modulus, `"m"` may be given as default and omitted in the enclosed
modexps. If all enclosed modexps are complete, the default values may be omitted.

`modexps` is an array of JSON objects containing an individual modexp with base, exponent and modulus in the following form
`{"b":"base", "e":"exponent", "m":"modulus"}`

After successful calculation of the modexps, the server returns the results either as full modexp objects with an additional
`"r"` attribute, or only as `{"r":"..."}`, depending on the value of `brief`.

*Note: values for base, exponent, modulus have to be given as hexadecimal strings. The result is returned as such a string as well.*

