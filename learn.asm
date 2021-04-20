# 递归程序

addiu $sp, $zero, 0x10010080 

# 定义变量, n 的阶乘，这里为5 => n = 5
addiu $s0, $zero, 5
# 压栈
sw $s0, 0($sp)
addiu $sp, $sp, -4

jal FACT
nop
j END
nop


FACT:
	# 压栈返回地址
	sw $ra, 0($sp)
	addi $sp, $sp, -4

	# 读取入参
	lw $s0, 8($sp)

	# 压栈返回值
	sw $zero, 0($sp)
	addiu $sp, $sp, -4

	# 递归 base 条件
	# if (n == 0) return 1
	bne $s0, $zero, RECURSION
	nop

	# 读取返回地址
	lw $t1, 8($sp)

	# 出栈：返回值，返回地址
	addiu $sp, $sp, 8

	# 压栈返回值
	addiu $s0, $zero, 1
	sw $s0, 0($sp)
	addiu $sp, $sp, -4

	jr $t1
	nop

RECURSION:	# recursion

	# return fact(n - 1) * n

	# 压栈参数
	addiu $s1, $s0, -1
	sw $s1, 0($sp)
	addi $sp, $sp, -4

	
	jal FACT
	nop
	
	# 现在的栈
	# 参数 | 返回地址 | 返回值 | 子函数的参数 | 子函数的返回值 | 当前 SP
	
	# 当前参数
	lw $s0, 20($sp)
	# 子函数返回值
	lw $s1, 4($sp)
	# 返回地址
	lw $t1, 16($sp)
	
	# 乘法运算
	mult $s1, $s0
	mflo $s2
	
	# 出栈
	addiu $sp, $sp 16
	
	# 返回值压栈
	sw $s2, 0($sp)
	addiu $sp, $sp, -4
	
	jr $t1
	nop

END:
