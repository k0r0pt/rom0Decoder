# Rom0Decoder
---
[![Build Status](https://travis-ci.org/k0r0pt/rom0Decoder.png?branch=master)](https://travis-ci.org/k0r0pt/rom0Decoder) [![codecov](https://codecov.io/gh/k0r0pt/rom0Decoder/branch/master/graph/badge.svg)](https://codecov.io/gh/k0r0pt/rom0Decoder)


---

## Function

Several routers (and ADSL modems) had a rom-0 vulnerability wherein an attacker could get a handle of the rom file for that router without having to login. Either way, the vulnerability has been patched for the most part. But this utility can still be helpful in decoding and fetching the router's password from that rom-0 file.

In the beginning, this repo has support for Zyxel (and similar) roms. We have plans to add support for more roms as we go along, if and when necessary.

## History

This code was originally written as an executable utility by [EtMatrix](https://github.com/etmatrix/rom0_decoder/) whence this repo has been forked.

## [Original Author](https://github.com/etmatrix)'s notes (whatever's still relevant)

Decode rom-0 file from zyxel,dlink etc

Original source C# download from [Hakim's website](http://www.hakim.ws/huawei/rom-0/Huawei-Firmware.zip)

## Modifications made

* Added gradle support
* Cleaned up the code a little.
* Added test cases (might be helpful in the future).
* Removed the executable part (this repo is supposed to be used as a library).
* Added Continuous Integration.