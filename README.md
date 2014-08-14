jmope
=====

This is an implementation of https://eprint.iacr.org/2013/129

Order-preserving encryption can be described as an encryption scheme that provides sort order of cipher-texts matching with the sort order of the plain-texts. In ideal case the cipher-texts should not provide any information about the plain-texts besides the order. But this is far from reality in order preserving encryption schemes except for the protocol introduced in https://eprint.iacr.org/2013/129. 

Mutable order-preserving encoding or mOPE achieves the ideal security goal for an order-preserving encryption scheme, which is IND-OCPA. Thus mOPE is chosen.
