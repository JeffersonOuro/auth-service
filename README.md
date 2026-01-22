# auth-service
# Autor Jefferson ouro

Serviços de autenticação de usuário

- Registro (POST /auth/register)
- O cliente envia os dados.
- AuthController chama AuthService.
- AuthService converte DTO → User e salva via UserRepository.
- Login (POST /auth/login)
- O cliente envia login e senha.
- AuthController autentica com AuthenticationManager.
- TokenService gera o JWT e retorna ao cliente.
- Acesso protegido (GET /users/me)
- O cliente envia requisição com Authorization: Bearer <token>.
- JwtAuthenticationFilter intercepta, valida o token com TokenService e carrega o usuário via UserRepository.
- Se válido, injeta o usuário no contexto (SecurityContextHolder).
- UserController retorna os dados do usuário logado.
