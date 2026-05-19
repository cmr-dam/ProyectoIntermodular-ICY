document.addEventListener('DOMContentLoaded', () => {
    
    // ==========================================
    // 1. MODO OSCURO
    // ==========================================
    const btnTema = document.getElementById('btn-tema');
    if (btnTema) {
        btnTema.addEventListener('click', () => {
            document.body.classList.toggle('dark-mode');
            if (document.body.classList.contains('dark-mode')) {
                btnTema.innerText = '☀️';
                btnTema.style.background = '#f1c40f';
                btnTema.style.color = '#000';
            } else {
                btnTema.innerText = '🌙';
                btnTema.style.background = '#333';
                btnTema.style.color = '#fff';
            }
        });
    }

    // ==========================================
    // 2. FETCH: FORMULARIO Y VALIDACIONES
    // ==========================================
    const formContacto = document.getElementById('form-contacto');
    
    if(formContacto) {
        formContacto.addEventListener('submit', async (evento) => {
            evento.preventDefault(); 

            const email = document.getElementById('email').value.trim();
            const telefono = document.getElementById('telefono').value.trim();
            const mensaje = document.getElementById('mensaje').value.trim();
            const esEmpresa = document.getElementById('es_empresa').checked;
            const respuestaUI = document.getElementById('form-respuesta');

            // Regex para Email y Teléfono por seguridad extra
            const regexEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
            if (!regexEmail.test(email)) {
                respuestaUI.style.color = "red";
                respuestaUI.innerText = idiomaActual === 'es' ? 'Por favor, introduce un correo electrónico válido.' : 'Please enter a valid email address.';
                return;
            }

            const regexTelefono = /^\+?[0-9]{9,15}$/;
            if (!regexTelefono.test(telefono)) {
                respuestaUI.style.color = "red";
                respuestaUI.innerText = idiomaActual === 'es' ? 'Teléfono inválido. Usa al menos 9 números.' : 'Invalid phone. Use at least 9 digits.';
                return;
            }

            // ENVIAR A PHP
            try {
                respuestaUI.style.color = "blue";
                respuestaUI.innerText = "Enviando...";

                const peticion = await fetch('api/guardar_contacto.php', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ email, telefono, mensaje, esEmpresa })
                });

                const resultado = await peticion.json();

                if(peticion.ok) {
                    respuestaUI.style.color = "green";
                    respuestaUI.innerText = idiomaActual === 'es' ? '¡Mensaje guardado en la base de datos!' : 'Message saved to database!';
                    formContacto.reset();
                } else {
                    throw new Error(resultado.mensaje);
                }
            } catch (error) {
                respuestaUI.style.color = "red";
                respuestaUI.innerText = "Error del servidor.";
            }
        });
    }

    // ==========================================
    // 3. TRADUCCIÓN GLOBAL
    // ==========================================
    const btnIdioma = document.getElementById('btn-idioma');
    let idiomaActual = 'es';

    const traducciones = {
        es: {
            nav_inicio: "Inicio", nav_tarifas: "Tarifas", nav_rutinas: "Rutinas", nav_sobre_nosotros: "Sobre nosotros", nav_formulario: "Contacto",
            hero_desc: "Software de gestión de gimnasios.", 
            tit_tarifas: "Tarifas", t1_tit: "Tarifa 1", t1_desc: "Prueba el software durante un mes.",
            t2_tit: "Tarifa 2", t2_desc: "Acceso mensual a nuestro software", t3_tit: "Tarifa 3", t3_desc: "Acceso anual a nuestro software",
            tit_historia: "Nuestra Historia", h1_tit: "Nuestros comienzos", h1_desc: "GymStats nació de una necesidad real: simplificar la gestión diaria de los gimnasios. Tras ver cómo muchos centros perdían tiempo con tareas manuales, decidimos crear una solución clara, rápida y eficiente.",
            h2_tit: "Crecimiento y evolución", h2_desc: "Con el paso del tiempo, GymStats fue evolucionando gracias al feedback de entrenadores y gestores. Añadimos funciones de control de clientes, pagos y estadísticas para facilitar la toma de decisiones.",
            h3_tit: "Mirando al futuro", h3_desc: "Nuestro objetivo es seguir creciendo junto a los gimnasios, ofreciendo un software moderno, intuitivo y adaptable. Queremos ser una herramienta clave en la gestión deportiva del futuro.",
            tit_contacto: "Contáctanos", label_email: "Correo Electrónico:", label_telefono: "Número de Teléfono:", label_mensaje: "Mensaje:", label_empresa: "¿Eres una empresa?", btn_enviar: "Enviar Solicitud", footer_terms: "Términos - Condiciones - Políticas de Privacidad",
            
            // Textos de la página de Rutinas
            tit_rutinas_main: "Gestión de Rutinas", desc_rutinas_main: "Asigna y controla los planes de entrenamiento de tus clientes.",
            r1_tit: "Push / Pull / Legs (5 Días)", r1_desc: "Ideal para hipertrofia y fuerza. Distribuye el volumen semanal entrenando empujes, tirones y pierna de forma eficiente de lunes a viernes.",
            r2_tit: "Grease the Groove", r2_desc: "Método de alta frecuencia para reventar estancamientos en ejercicios específicos (como dominadas). Series submáximas repartidas durante el día.",
            r3_tit: "Acondicionamiento Base", r3_desc: "Rutina Full Body de 3 días enfocada en principiantes para adaptar tendones y ganar fuerza base antes de pasar a divisiones más avanzadas."
        },
        en: {
            nav_inicio: "Home", nav_tarifas: "Pricing", nav_rutinas: "Routines", nav_sobre_nosotros: "About Us", nav_formulario: "Contact",
            hero_desc: "Gym management software.", 
            tit_tarifas: "Pricing", t1_tit: "Plan 1", t1_desc: "Try the software for one month.",
            t2_tit: "Plan 2", t2_desc: "Monthly access to our software", t3_tit: "Plan 3", t3_desc: "Yearly access to our software",
            tit_historia: "Our History", h1_tit: "Our Beginnings", h1_desc: "GymStats was born from a real need: simplifying daily gym management. After seeing centers waste time on manual tasks, we created a fast and clear solution.",
            h2_tit: "Growth and Evolution", h2_desc: "Over time, GymStats evolved thanks to feedback from trainers and managers. We added client control, payments, and statistics.",
            h3_tit: "Looking to the Future", h3_desc: "Our goal is to keep growing alongside gyms, offering modern and adaptable software for the future of sports management.",
            tit_contacto: "Contact Us", label_email: "Email Address:", label_telefono: "Phone Number:", label_mensaje: "Message:", label_empresa: "Are you a company?", btn_enviar: "Send Request", footer_terms: "Terms - Conditions - Privacy Policies",
            
            // Textos de la página de Rutinas
            tit_rutinas_main: "Routine Management", desc_rutinas_main: "Assign and track training plans for your clients.",
            r1_tit: "Push / Pull / Legs (5 Days)", r1_desc: "Ideal for hypertrophy and strength. Distribute weekly volume efficiently training push, pull, and legs from Monday to Friday.",
            r2_tit: "Grease the Groove", r2_desc: "High-frequency method to break plateaus in specific exercises (like pull-ups). Submaximal sets spread throughout the day.",
            r3_tit: "Base Conditioning", r3_desc: "3-day Full Body routine focused on beginners to adapt tendons and build base strength before moving to advanced splits."
        }
    };

    if (btnIdioma) {
        btnIdioma.addEventListener('click', () => {
            idiomaActual = idiomaActual === 'es' ? 'en' : 'es';
            btnIdioma.innerText = idiomaActual === 'es' ? 'EN' : 'ES';
            
            document.querySelectorAll('[data-lang]').forEach(elemento => {
                const clave = elemento.getAttribute('data-lang');
                if(traducciones[idiomaActual][clave]) {
                    elemento.innerText = traducciones[idiomaActual][clave];
                }
            });
        });
    }
});